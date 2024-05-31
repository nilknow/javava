package com.nilknow.classfile;

public interface AccessFlags {
    int ACC_PUBLIC = 0x0001; // Indicates a public method
    int ACC_PRIVATE = 0x0002; // Indicates a private method
    int ACC_PROTECTED = 0x0004; // Indicates a protected method
    int ACC_STATIC = 0x0008; // Indicates a static method
    int ACC_FINAL = 0x0010; // Indicates a final method
    int ACC_SUPER = 0x0020;
}