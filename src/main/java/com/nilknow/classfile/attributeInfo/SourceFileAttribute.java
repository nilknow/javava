package com.nilknow.classfile.attributeInfo;

public class SourceFileAttribute extends AttributeInfo{
    int sourceFileIndex;

    public SourceFileAttribute(int nameIndex, int length,int sourceFileIndex) {
        super(nameIndex, length);
        this.sourceFileIndex = sourceFileIndex;
    }
}
