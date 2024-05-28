package com.nilknow.classfile;

import lombok.Data;

@Data
public class MethodInfo {
    private final int accessFlags;

    private final int nameIndex;

    private final int descriptorIndex;

    private final AttributeInfo[] attributes;
}
