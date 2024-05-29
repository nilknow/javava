package com.nilknow.classfile.constantInfo;

import lombok.Data;

@Data
public abstract class ConstantInfo {
    public static final int CONSTANT_Utf8 = 1;
    public static final int CONSTANT_Class = 7;
    public static final int CONSTANT_Methodref = 10;
    public static final int CONSTANT_NameAndType = 12;

    public abstract byte getTag();
}
