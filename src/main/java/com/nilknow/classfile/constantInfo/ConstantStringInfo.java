package com.nilknow.classfile.constantInfo;

public class ConstantStringInfo extends ConstantInfo {
    public static final byte TAG = ConstantInfo.CONSTANT_String;

    private final int stringIndex;

    public ConstantStringInfo(int stringIndex) {
        this.stringIndex = stringIndex;
    }

    @Override
    public byte getTag() {
        return TAG;
    }

    public int getStringIndex() {
        return stringIndex;
    }
}