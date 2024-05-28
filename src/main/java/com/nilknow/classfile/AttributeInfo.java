package com.nilknow.classfile;

import lombok.Data;

@Data
public class AttributeInfo {
    private final int nameIndex;
    private final int length;
    private final byte[] info;
}
