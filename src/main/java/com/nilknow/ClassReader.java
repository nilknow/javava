package com.nilknow;

import com.nilknow.classfile.AttributeInfo;
import com.nilknow.classfile.ConstantPool;
import com.nilknow.classfile.FieldInfo;
import com.nilknow.classfile.MethodInfo;

import java.io.EOFException;

public interface ClassReader {
    int readByte() throws EOFException;

    int readUint16() throws EOFException;

    long readUint32() throws EOFException;

    long readUint64() throws EOFException;

    ConstantPool readConstantPool();

    int[] readInterfaces();

    FieldInfo[] readFields();

    MethodInfo[] readMethods();

    AttributeInfo[] readAttributes();
}
