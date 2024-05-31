package com.nilknow.classfile.attributeInfo;

public class LineNumberTableAttribute extends AttributeInfo {
    private final int lineNumberTableLength;
    private final LineNumberTableEntry[] lineNumberTable;

    public LineNumberTableAttribute(int nameIndex,
                                    int length,
                                    String attrName,
                                    int lineNumberTableLength,
                                    LineNumberTableEntry[] lineNumberTable) {
        super(nameIndex, length,attrName);
        this.lineNumberTableLength = lineNumberTableLength;
        this.lineNumberTable = lineNumberTable;
    }
}


