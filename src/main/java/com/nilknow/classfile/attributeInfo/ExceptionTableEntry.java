package com.nilknow.classfile.attributeInfo;

import lombok.Data;

@Data
public class ExceptionTableEntry {

    private final int startPc;
    private final int endPc;
    private final int handlerPc;
    private final int catchTypeIndex;

    public ExceptionTableEntry(int startPc, int endPc, int handlerPc, int catchTypeIndex) {
        this.startPc = startPc;
        this.endPc = endPc;
        this.handlerPc = handlerPc;
        this.catchTypeIndex = catchTypeIndex;
    }
}