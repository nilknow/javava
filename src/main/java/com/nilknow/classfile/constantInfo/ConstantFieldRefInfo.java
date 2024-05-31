package com.nilknow.classfile.constantInfo;

public class ConstantFieldRefInfo extends ConstantInfo {
    public static final byte TAG = ConstantInfo.CONSTANT_Fieldref;

    private final int classIndex;
    private final int nameAndTypeIndex;

    public ConstantFieldRefInfo(int classIndex, int nameAndTypeIndex) {
        this.classIndex = classIndex;
        this.nameAndTypeIndex = nameAndTypeIndex;
    }

    @Override
    public byte getTag() {
        return TAG;
    }

    public int getClassIndex() {
        return classIndex;
    }

    public int getNameAndTypeIndex() {
        return nameAndTypeIndex;
    }

}
