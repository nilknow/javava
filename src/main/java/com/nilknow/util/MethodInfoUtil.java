package com.nilknow.util;

import com.nilknow.classfile.ConstantPool;
import com.nilknow.classfile.MethodInfo;
import com.nilknow.classfile.attributeInfo.CodeAttribute;

import java.util.Arrays;

public class MethodInfoUtil {
    public static String getMethodName(MethodInfo methodInfo, ConstantPool constantPool) {
        int nameIndex = methodInfo.getNameIndex();
        return constantPool.getUtf8(nameIndex);
    }

    public static String getMethodDescription(MethodInfo methodInfo, ConstantPool constantPool) {
        int descriptorIndex = methodInfo.getDescriptorIndex();
        return constantPool.getUtf8(descriptorIndex);
    }

    public static CodeAttribute getCodeAttribute(MethodInfo methodInfo) {
        return Arrays.stream(methodInfo.getAttributes())
                .filter(x -> "Code".equals(x.getAttrName()))
                .findFirst()
                .map(x -> (CodeAttribute) x)
                .orElse(null);
    }
}
