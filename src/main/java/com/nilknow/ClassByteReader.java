package com.nilknow;

import com.nilknow.classfile.*;
import com.nilknow.classfile.attributeInfo.*;
import com.nilknow.classfile.constantInfo.*;
import com.nilknow.util.DecodeUtil;

import java.io.EOFException;

public class ClassByteReader implements ClassReader {
    private final byte[] data;
    private int pos;

    public ClassByteReader(byte[] data) {
        this.data = data;
        this.pos = 0;
    }

    @Override
    public int readUint8() throws EOFException {
        if (pos >= data.length) {
            throw new EOFException("Reached end of data");
        }
        return data[pos++] & 0xFF; // Mask to get unsigned byte value
    }

    @Override
    public byte readByte() throws EOFException {
        if (pos >= data.length) {
            throw new EOFException("Reached end of data");
        }
        return data[pos++];
    }

    @Override
    public int readUint16() throws EOFException {
        int b1 = readUint8();
        int b2 = readUint8();
        return (b1 << 8) | b2; // Combine bytes into short (big-endian)
    }

    @Override
    public long readUint32() throws EOFException {
        int b1 = readUint8();
        int b2 = readUint8();
        int b3 = readUint8();
        int b4 = readUint8();
        return ((long) b1 << 24) | ((long) b2 << 16) | ((long) b3 << 8) | b4; // Combine bytes into int (big-endian)
    }

    @Override
    public long readUint64() throws EOFException {
        int b1 = readUint8();
        int b2 = readUint8();
        int b3 = readUint8();
        int b4 = readUint8();
        int b5 = readUint8();
        int b6 = readUint8();
        int b7 = readUint8();
        int b8 = readUint8();
        return (((long) b1 << 56) | ((long) b2 << 48) | ((long) b3 << 40) | ((long) b4 << 32)
                | ((long) b5 << 24) | ((long) b6 << 16) | ((long) b7 << 8) | b8); // Combine bytes into long (big-endian)
    }

    @Override
    public byte[] readBytes(int length) throws EOFException {
        byte[] bytes = new byte[length];
        for (int i = 0; i < length; i++) {
            bytes[i] = readByte();
        }
        return bytes;
    }

    @Override
    public ConstantPool readConstantPool() throws EOFException {
        int cpCount = readUint16();
        ConstantPool cp = new ConstantPool(cpCount);
        for (int i = 1; i < cpCount; i++) {
            cp.getConstantInfos().add(readConstantInfo());
        }
        return cp;
    }

    @Override
    public ConstantInfo readConstantInfo() throws EOFException {
        int tag = readUint8();
        switch (tag) {
            case ConstantInfo.CONSTANT_Utf8:
                int length = readUint16();
                byte[] bytes = readBytes(length);
                return new ConstantUtf8Info(DecodeUtil.decode(bytes));
            case ConstantInfo.CONSTANT_Class:
                return new ConstantClassInfo(readUint16());
            case ConstantInfo.CONSTANT_String:
                return new ConstantStringInfo(readUint16());
            case ConstantInfo.CONSTANT_Fieldref:
                return new ConstantFieldRefInfo(readUint16(), readUint16());
            case ConstantInfo.CONSTANT_Methodref:
                return new ConstantMethodRefInfo(readUint16(), readUint16());
            case ConstantInfo.CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo(readUint16(), readUint16());
        }
        return null;
    }

    @Override
    public int[] readInterfaces() throws EOFException {
        int count = readUint16();
        return new int[0];
    }

    @Override
    public FieldInfo[] readFields() throws EOFException {
        int count = readUint16();
        FieldInfo[] fieldInfos = new FieldInfo[count];
        for (int i = 0; i < count; i++) {
            fieldInfos[i] = new FieldInfo(readUint16(), readUint16(), readUint16());
        }
        return fieldInfos;
    }

    @Override
    public MethodInfo[] readMethods(ConstantPool cp) throws EOFException {
        int count = readUint16();
        MethodInfo[] methodInfos = new MethodInfo[count];
        for (int i = 0; i < count; i++) {
            int accessFlags = readUint16();
            int nameIndex = readUint16();
            int descriptorIndex = readUint16();
            AttributeInfo[] attributeInfos = readAttributes(cp);
            methodInfos[i] = new MethodInfo(
                    accessFlags,
                    nameIndex,
                    descriptorIndex,
                    attributeInfos
            );
        }
        return methodInfos;
    }

    @Override
    public AttributeInfo[] readAttributes(ConstantPool cp) throws EOFException {
        int count = readUint16();
        AttributeInfo[] attributeInfos = new AttributeInfo[count];
        for (int i = 0; i < count; i++) {
            attributeInfos[i] = readAttribute(cp);
        }
        return attributeInfos;
    }

    @Override
    public AttributeInfo readAttribute(ConstantPool cp) throws EOFException {
        int attrNameIndex = readUint16();
        String attrName = cp.getUtf8(attrNameIndex);
        long attrLength = readUint32();
        switch (attrName) {
            case "Code":
                int maxStack = readUint16();
                int maxLocals = readUint16();
                int codeLength = (int) readUint32();
                byte[] code = readBytes(codeLength);

                int exceptionTableLength = readUint16();
                ExceptionTableEntry[] exceptionTable = new ExceptionTableEntry[exceptionTableLength];
                for (int i = 0; i < exceptionTableLength; i++) {
                    exceptionTable[i] = new ExceptionTableEntry(readUint16(), readUint16(), readUint16(), readUint16());
                }

                int attributesCount = readUint16();
                AttributeInfo[] attributes = new AttributeInfo[attributesCount];
                for (int i = 0; i < attributesCount; i++) {
                    attributes[i] = readAttribute(cp);
                }
                return new CodeAttribute(
                        attrNameIndex,
                        (int) attrLength,
                        attrName,
                        maxStack,
                        maxLocals,
                        codeLength,
                        code,
                        exceptionTable,
                        attributesCount,
                        attributes
                );
            case "LineNumberTable":
                int lineNumberTableLength = readUint16();
                LineNumberTableEntry[] lineNumberTableEntries = new LineNumberTableEntry[lineNumberTableLength];
                for (int i = 0; i < lineNumberTableLength; i++) {
                    lineNumberTableEntries[i] = new LineNumberTableEntry(readUint16(), readUint16());
                }
                return new LineNumberTableAttribute(attrNameIndex, (int) attrLength, attrName, lineNumberTableLength, lineNumberTableEntries);
            case "SourceFile":
                int sourceFileIndex = readUint16();
                return new SourceFileAttribute(attrNameIndex, (int) attrLength, attrName, sourceFileIndex);
            default:
                System.out.println("Unknown attribute: " + attrName);
        }
        return null;
    }
}
