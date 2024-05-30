package com.nilknow.classfile.attributeInfo;

public class LineNumberTableAttribute extends AttributeInfo {
    private final int lineNumberTableLength;
    private final LineNumberTableEntry[] lineNumberTable;

    public LineNumberTableAttribute(int nameIndex,
                                    int length,
                                    int lineNumberTableLength,
                                    LineNumberTableEntry[] lineNumberTable) {
        super(nameIndex, length);
        this.lineNumberTableLength = lineNumberTableLength;
        this.lineNumberTable = lineNumberTable;
    }
}


