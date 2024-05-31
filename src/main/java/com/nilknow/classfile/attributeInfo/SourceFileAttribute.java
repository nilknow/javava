package com.nilknow.classfile.attributeInfo;

public class SourceFileAttribute extends AttributeInfo {
    int sourceFileIndex;

    public SourceFileAttribute(int nameIndex, int length, String attrName, int sourceFileIndex) {
        super(nameIndex, length, attrName);
        this.sourceFileIndex = sourceFileIndex;
    }
}
