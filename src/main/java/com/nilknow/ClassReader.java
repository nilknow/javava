package com.nilknow;

import com.nilknow.classfile.*;
import com.nilknow.classfile.constantInfo.ConstantInfo;

import java.io.EOFException;

public interface ClassReader {
    int readUint8() throws EOFException;

    int readUint16() throws EOFException;

    long readUint32() throws EOFException;

    long readUint64() throws EOFException;

    byte[] readBytes(int length) throws EOFException;

    ConstantPool readConstantPool() throws EOFException;

    ConstantInfo readConstantInfo() throws EOFException;

    int[] readInterfaces();

    FieldInfo[] readFields();

    MethodInfo[] readMethods();

    AttributeInfo[] readAttributes();
}
