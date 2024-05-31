package com.nilknow.classfile;

import com.nilknow.classfile.attributeInfo.AttributeInfo;
import lombok.Data;

@Data
public class MethodInfo {

    private final int accessFlags;
    private final int nameIndex;
    private final int descriptorIndex;
    private final AttributeInfo[] attributes;

    public MethodInfo(int accessFlags, int nameIndex, int descriptorIndex, AttributeInfo[] attributes) {
        this.accessFlags = accessFlags;
        this.nameIndex = nameIndex;
        this.descriptorIndex = descriptorIndex;
        this.attributes = attributes;
    }

    public boolean isStatic() {
        return (accessFlags & AccessFlags.ACC_STATIC) != 0;
    }

}
