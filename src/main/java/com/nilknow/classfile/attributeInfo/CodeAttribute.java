package com.nilknow.classfile.attributeInfo;

import lombok.Data;

@Data
public class CodeAttribute extends AttributeInfo {

    private final int maxStack;
    private final int maxLocals;
    private final int codeLength;
    private final byte[] code;
    private final ExceptionTableEntry[] exceptionTable;
    private final int attributesCount;
    private final AttributeInfo[] attributes;

    public CodeAttribute(int nameIndex, int length, int maxStack, int maxLocals, int codeLength, byte[] code, ExceptionTableEntry[] exceptionTable, int attributesCount, AttributeInfo[] attributes) {
        super(nameIndex, length);
        this.maxStack = maxStack;
        this.maxLocals = maxLocals;
        this.codeLength = codeLength;
        this.code = code;
        this.exceptionTable = exceptionTable;
        this.attributesCount = attributesCount;
        this.attributes = attributes;
    }
}