package com.nilknow.classfile.constantInfo;

import lombok.Data;

@Data
public class ConstantNameAndTypeInfo extends ConstantInfo {

    public static final byte TAG = ConstantInfo.CONSTANT_NameAndType;

    private final int nameIndex;
    private final int descriptorIndex;

    public ConstantNameAndTypeInfo(int nameIndex, int descriptorIndex) {
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }

    @Override
    public byte getTag() {
        return TAG;
    }

    public int getNameIndex() {
        return nameIndex;
    }

    public int getDescriptorIndex() {
        return descriptorIndex;
    }
}