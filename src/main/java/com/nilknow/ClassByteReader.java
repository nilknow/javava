package com.nilknow;

import com.nilknow.classfile.AttributeInfo;
import com.nilknow.classfile.ConstantPool;
import com.nilknow.classfile.FieldInfo;
import com.nilknow.classfile.MethodInfo;

import java.io.EOFException;

public class ClassByteReader implements ClassReader{
    private final byte[] data;
    private int pos;

    public ClassByteReader(byte[] data) {
        this.data = data;
        this.pos = 0;
    }

    @Override
    public int readByte() throws EOFException {
        if (pos >= data.length) {
            throw new EOFException("Reached end of data");
        }
        return data[pos++] & 0xFF; // Mask to get unsigned byte value
    }

    @Override
    public int readUint16() throws EOFException {
        int b1 = readByte();
        int b2 = readByte();
        return (b1 << 8) | b2; // Combine bytes into short (big-endian)
    }

    @Override
    public long readUint32() throws EOFException {
        int b1 = readByte();
        int b2 = readByte();
        int b3 = readByte();
        int b4 = readByte();
        return ((long) b1 << 24) | ((long) b2 << 16) | ((long) b3 << 8) | b4; // Combine bytes into int (big-endian)
    }

    @Override
    public long readUint64() throws EOFException {
        int b1 = readByte();
        int b2 = readByte();
        int b3 = readByte();
        int b4 = readByte();
        int b5 = readByte();
        int b6 = readByte();
        int b7 = readByte();
        int b8 = readByte();
        return (((long) b1 << 56) | ((long) b2 << 48) | ((long) b3 << 40) | ((long) b4 << 32)
                | ((long) b5 << 24) | ((long) b6 << 16) | ((long) b7 << 8) | b8); // Combine bytes into long (big-endian)
    }

    @Override
    public ConstantPool readConstantPool() {
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
