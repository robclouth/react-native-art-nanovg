import _ from 'lodash';
const MOVE_TO = 0;
const CLOSE = 1;
const LINE_TO = 2;
const CURVE_TO = 3;
const ARC = 4;
const CIRCLE = 5;
const ELLIPSE = 6;

export default class SerializablePath {
    constructor(path) {
        this.reset();
        if (path instanceof SerializablePath) {
            this.path = path.path.slice();
        } else if (path) {
            this.push(path);
        }
    }

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

    addCircle = (cx, cy, r) => {
        this.path.push(CIRCLE, cx, cy, r);
        return this;
    };

    addEllipse = (cx, cy, rx, ry) => {
        this.path.push(ELLIPSE, cx, cy, rx, ry);
        return this;
    }

    close = () => {
        if (!_.isNil(this.penDownX)) {
            this.penX = this.penDownX;
            this.penY = this.penDownY;
            this.path.push(CLOSE);
            this.penDownX = null;
        }
        return this;
    };

    toJSON = () => {
        return this.path;
    };
}
