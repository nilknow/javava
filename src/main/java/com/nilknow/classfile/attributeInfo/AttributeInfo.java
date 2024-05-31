package com.nilknow.classfile.attributeInfo;

import lombok.Data;

@Data
public abstract class AttributeInfo {
    private final int nameIndex;
    private final int length;
    private final String attrName;
}
