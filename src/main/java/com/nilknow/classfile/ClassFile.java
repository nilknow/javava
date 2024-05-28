package com.nilknow.classfile;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ClassFile {
    private long magicNumber;
    private int minorVersion;
    private int majorVersion;
    private ConstantPool constantPool;
    private int accessFlags;
    private int thisClass;
    private int superClass;
    private int[] interfaces;
    private FieldInfo[] fieldInfos;
    private MethodInfo[] methods;
    private AttributeInfo[] attributes;
}
