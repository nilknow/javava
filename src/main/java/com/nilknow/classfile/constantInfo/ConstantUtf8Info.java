package com.nilknow.classfile.constantInfo;

import lombok.Data;

@Data
public class ConstantUtf8Info extends ConstantInfo {

    public static final byte TAG = ConstantInfo.CONSTANT_Utf8;

    private final String value;

    public ConstantUtf8Info(String value) {
        this.value = value;
    }

    @Override
    public byte getTag() {
        return TAG;
    }

    public String getValue() {
        return value;
    }
}
