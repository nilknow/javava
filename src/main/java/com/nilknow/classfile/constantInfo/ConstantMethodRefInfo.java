package com.nilknow.classfile.constantInfo;

import lombok.Data;

@Data
public class ConstantMethodRefInfo extends ConstantInfo {
    private static final int TAG = ConstantInfo.CONSTANT_Methodref;
    private final int classIndex;
    private final int nameAndTypeIndex;

    public ConstantMethodRefInfo(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

    @Override
    public byte getTag() {
        return TAG;
    }
}