package com.nilknow.classfile;

import com.nilknow.ClassReader;
import lombok.Data;

import java.io.EOFException;

@Data
public class ClassFileReader {
    private ClassFile classFile;
    private ClassReader classReader;

    public ClassFileReader(ClassReader classReader) {
        this.classReader = classReader;
    }

    public ClassFile read() throws EOFException {
        ClassFile cf = new ClassFile();

        long magicNumber = this.classReader.readUint32();
        int minorVersion = this.classReader.readUint16();
        int majorVersion = this.classReader.readUint16();
        ConstantPool constantPool= this.classReader.readConstantPool();
        int accessFlags = this.classReader.readUint16();
        int thisClass = this.classReader.readUint16();
        int superClass = this.classReader.readUint16();
        int[] interfaceInfos = this.classReader.readInterfaces();
        FieldInfo[] fields=this.classReader.readFields();
        MethodInfo[] methods=this.classReader.readMethods();
        AttributeInfo[] attributes=this.classReader.readAttributes();

        cf.setMagicNumber(magicNumber);
        cf.setMinorVersion(minorVersion);
        cf.setMajorVersion(majorVersion);
        cf.setConstantPool(constantPool);
        cf.setAccessFlags(accessFlags);
        cf.setThisClass(thisClass);
        cf.setSuperClass(superClass);
        cf.setInterfaces(interfaceInfos);
        cf.setFieldInfos(fields);
        cf.setMethods(methods);
        cf.setAttributes(attributes);
        return cf;
    }
}
