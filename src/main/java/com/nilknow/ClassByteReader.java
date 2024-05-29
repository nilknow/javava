package com.nilknow;

import com.nilknow.classfile.*;
import com.nilknow.classfile.constantInfo.*;
import com.nilknow.util.DecodeUtil;

import java.io.EOFException;

public class ClassByteReader implements ClassReader{
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
        System.arraycopy(data, pos, bytes, 0, length);
        pos += length;
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
            case ConstantInfo.CONSTANT_Methodref:
                return new ConstantMethodRefInfo(readUint16(), readUint16());
            case ConstantInfo.CONSTANT_NameAndType:
                return new ConstantNameAndTypeInfo(readUint16(), readUint16());
        }
        return null;
    }

    @Override
    public int[] readInterfaces() {
        return new int[0];
    }

    @Override
    public FieldInfo[] readFields() {
        return new FieldInfo[0];
    }

    @Override
    public MethodInfo[] readMethods() {
        return new MethodInfo[0];
    }

    @Override
    public AttributeInfo[] readAttributes() {
        return new AttributeInfo[0];
    }
}
