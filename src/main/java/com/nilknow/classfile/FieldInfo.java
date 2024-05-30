package com.nilknow.classfile;

import lombok.Data;

@Data
public class FieldInfo {
    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;

    public FieldInfo(int accessFlags, int nameIndex, int descriptorIndex) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
    }
}
