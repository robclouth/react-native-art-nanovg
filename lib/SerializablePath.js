import _ from 'lodash';
const MOVE_TO = 0;
const CLOSE = 1;
const LINE_TO = 2;
const CURVE_TO = 3;
const ARC = 4;
const CIRCLE = 5;
const ELLIPSE = 6;
const RECTANGLE = 7;
const ROUNDED_RECTANGLE = 8;
const HOLE = 9;

export default class SerializablePath {
    constructor(path) {
        this.reset();
        if (path instanceof SerializablePath) {
            this.path = path.path.slice();
        } else if (path) {
            this.push(path);
        }
    }

    /* parser */
    push = () => {
        let p = Array.prototype.join.call(arguments, ' ')
            .replace(/(\.\d+)(?=\-?\.)/ig, '$1,') //-.3-.575 => -.3,-.575
            .match(/[a-df-z]|[\-+]?(?:[\d\.]e[\-+]?|[^\s\-+,a-z])+/ig);

        if (!p) {
            return this;
        }

        let last,
            cmd = p[0],
            i = 1;

        while (cmd){
            switch (cmd){
                case 'm': this.move(p[i++], p[i++]); break;
                case 'l': this.line(p[i++], p[i++]); break;
                case 'c': this.curve(p[i++], p[i++], p[i++], p[i++], p[i++], p[i++]); break;
                case 's': this.curve(p[i++], p[i++], null, null, p[i++], p[i++]); break;
                case 'q': this.curve(p[i++], p[i++], p[i++], p[i++]); break;
                case 't': this.curve(p[i++], p[i++]); break;
                case 'a': this.arc(p[i + 5], p[i + 6], p[i], p[i + 1], p[i + 3], !+p[i + 4], +p[i + 2]); i += 7; break;
                case 'h': this.line(p[i++], 0); break;
                case 'v': this.line(0, p[i++]); break;

                case 'M': this.moveTo(p[i++], p[i++]); break;
                case 'L': this.lineTo(p[i++], p[i++]); break;
                case 'C': this.curveTo(p[i++], p[i++], p[i++], p[i++], p[i++], p[i++]); break;
                case 'S': this.curveTo(p[i++], p[i++], null, null, p[i++], p[i++]); break;
                case 'Q': this.curveTo(p[i++], p[i++], p[i++], p[i++]); break;
                case 'T': this.curveTo(p[i++], p[i++]); break;
                case 'A': this.arcTo(p[i + 5], p[i + 6], p[i], p[i + 1], p[i + 3], !+p[i + 4], +p[i + 2]); i += 7; break;
                case 'H': this.lineTo(p[i++], this.penY); break;
                case 'V': this.lineTo(this.penX, p[i++]); break;

                case 'Z': case 'z': this.close(); break;
                default:
                    cmd = last;
                    i--;
                    continue;
            }

            last = cmd;
            if (last === 'm') {
                last = 'l';
            }
            else if (last === 'M') {
                last = 'L';
            }
            cmd = p[i++];
        }
        return this;
    };

    /* utility methods */

    reset = () => {
        this.penX = this.penY = 0;
        this.penDownX = this.penDownY = null;
        this.path = [];
        return this;
    };

    move = (x,y) => {
        this.moveTo(this.penX + (+x), this.penY + (+y));
        return this;
    };

    moveTo = (x,y) => {
        this.path.push(MOVE_TO, this.penX = (+x), this.penY = (+y));
        return this;
    };

    line = (x,y) => {
        return this.lineTo(this.penX + (+x), this.penY + (+y));
    };

    lineTo = (x,y) => {
        if (_.isNil(this.penDownX)) {
            this.penDownX = this.penX; this.penDownY = this.penY;
        }
        this.path.push(LINE_TO, this.penX = (+x), this.penY = (+y));
        return this;
    };

    curve = (c1x, c1y, c2x, c2y, ex, ey) => {
        let x = this.penX,
            y = this.penY;

        return this.curveTo(
            x + (+c1x),
            y + (+c1y),
            x + (+c2x),
            y + (+c2y),
            x + (+ex),
            y + (+ey)
        );
    };

    curveTo = (c1x, c1y, c2x, c2y, ex, ey) => {
        let x = this.penX,
            y = this.penY;

        if (_.isNil(this.penDownX)) {
            this.penDownX = x; this.penDownY = y;
        }
        this.path.push(CURVE_TO, +c1x, +c1y, +c2x, +c2y, this.penX = +ex, this.penY = +ey);
        return this;
    };

    addArc = (cx, cy, r, a1, a2, clockwise) => {
        this.path.push(ARC, cx, cy, r, a1, a2, clockwise? 2 : 1);
        return this;
    };

    addCircle = (cx, cy, r, isHole) => {
        this.path.push(CIRCLE, cx, cy, r, isHole !== undefined? isHole : false);
        return this;
    };

    addEllipse = (cx, cy, rx, ry, isHole) => {
        this.path.push(ELLIPSE, cx, cy, rx, ry, isHole !== undefined? isHole : false);
        return this;
    }

    addRectangle = (x, y, w, h, isHole) => {
        this.path.push(RECTANGLE, x, y, w, h, isHole !== undefined? isHole : false);
        return this;
    };

    addRoundedRectangle = (x, y, w, h, rTopLeft, rTopRight, rBottomRight, rBottomLeft, isHole) => {
        if(rTopRight === undefined || typeof(rTopRight) === 'boolean')
            this.path.push(ROUNDED_RECTANGLE, x, y, w, h, rTopLeft, rTopLeft, rTopLeft, rTopLeft, rTopRight !== undefined? rTopRight : false);
        else
            this.path.push(ROUNDED_RECTANGLE, x, y, w, h, rTopLeft, rTopRight, rBottomRight, rBottomLeft, isHole !== undefined? isHole : false);
        return this;
    };

    hole = () => {
        this.path.push(HOLE);
        return this;
    };

    close = (isHole) => {
        if (!_.isNil(this.penDownX)) {
            this.penX = this.penDownX;
            this.penY = this.penDownY;
            this.path.push(CLOSE, isHole !== undefined? isHole : false);
            this.penDownX = null;
        }
        return this;
    };

    toJSON = () => {
        return this.path;
    };
}
