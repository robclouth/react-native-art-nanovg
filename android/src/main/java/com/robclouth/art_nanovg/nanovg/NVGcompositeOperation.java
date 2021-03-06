/* ----------------------------------------------------------------------------
 * This file was automatically generated by SWIG (http://www.swig.org).
 * Version 3.0.8
 *
 * Do not make changes to this file unless you know what you are doing--modify
 * the SWIG interface file instead.
 * ----------------------------------------------------------------------------- */

package com.robclouth.art_nanovg.nanovg;

public final class NVGcompositeOperation {
  public final static NVGcompositeOperation NVG_SOURCE_OVER = new NVGcompositeOperation("NVG_SOURCE_OVER");
  public final static NVGcompositeOperation NVG_SOURCE_IN = new NVGcompositeOperation("NVG_SOURCE_IN");
  public final static NVGcompositeOperation NVG_SOURCE_OUT = new NVGcompositeOperation("NVG_SOURCE_OUT");
  public final static NVGcompositeOperation NVG_ATOP = new NVGcompositeOperation("NVG_ATOP");
  public final static NVGcompositeOperation NVG_DESTINATION_OVER = new NVGcompositeOperation("NVG_DESTINATION_OVER");
  public final static NVGcompositeOperation NVG_DESTINATION_IN = new NVGcompositeOperation("NVG_DESTINATION_IN");
  public final static NVGcompositeOperation NVG_DESTINATION_OUT = new NVGcompositeOperation("NVG_DESTINATION_OUT");
  public final static NVGcompositeOperation NVG_DESTINATION_ATOP = new NVGcompositeOperation("NVG_DESTINATION_ATOP");
  public final static NVGcompositeOperation NVG_LIGHTER = new NVGcompositeOperation("NVG_LIGHTER");
  public final static NVGcompositeOperation NVG_COPY = new NVGcompositeOperation("NVG_COPY");
  public final static NVGcompositeOperation NVG_XOR = new NVGcompositeOperation("NVG_XOR");

  public final int swigValue() {
    return swigValue;
  }

  public String toString() {
    return swigName;
  }

  public static NVGcompositeOperation swigToEnum(int swigValue) {
    if (swigValue < swigValues.length && swigValue >= 0 && swigValues[swigValue].swigValue == swigValue)
      return swigValues[swigValue];
    for (int i = 0; i < swigValues.length; i++)
      if (swigValues[i].swigValue == swigValue)
        return swigValues[i];
    throw new IllegalArgumentException("No enum " + NVGcompositeOperation.class + " with value " + swigValue);
  }

  private NVGcompositeOperation(String swigName) {
    this.swigName = swigName;
    this.swigValue = swigNext++;
  }

  private NVGcompositeOperation(String swigName, int swigValue) {
    this.swigName = swigName;
    this.swigValue = swigValue;
    swigNext = swigValue+1;
  }

  private NVGcompositeOperation(String swigName, NVGcompositeOperation swigEnum) {
    this.swigName = swigName;
    this.swigValue = swigEnum.swigValue;
    swigNext = this.swigValue+1;
  }

  private static NVGcompositeOperation[] swigValues = { NVG_SOURCE_OVER, NVG_SOURCE_IN, NVG_SOURCE_OUT, NVG_ATOP, NVG_DESTINATION_OVER, NVG_DESTINATION_IN, NVG_DESTINATION_OUT, NVG_DESTINATION_ATOP, NVG_LIGHTER, NVG_COPY, NVG_XOR };
  private static int swigNext = 0;
  private final int swigValue;
  private final String swigName;
}

