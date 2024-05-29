package com.nilknow.classfile.constantInfo;

import lombok.Data;

@Data
public class ConstantClassInfo extends ConstantInfo {

    public static final byte TAG = ConstantInfo.CONSTANT_Class;

    private final int nameIndex;

    public ConstantClassInfo(int nameIndex) {
        this.nameIndex = nameIndex;
    }

    @Override
    public byte getTag() {
        return TAG;
    }

    public int getNameIndex() {
        return nameIndex;
    }
}
