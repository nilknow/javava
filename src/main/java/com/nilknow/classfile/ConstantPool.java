package com.nilknow.classfile;

import com.nilknow.classfile.constantInfo.ConstantInfo;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConstantPool {
    private List<ConstantInfo> constantInfos;

    public ConstantPool(int size) {
        this.constantInfos = new ArrayList<>(size);
    }

    @Override
    public String toString() {
        return "ConstantPool{" +
                "constantInfosSize=" + constantInfos.size() +
                '}';
    }
}
