package com.nilknow.classfile;

import com.nilknow.classfile.constantInfo.ConstantInfo;
import com.nilknow.classfile.constantInfo.ConstantStringInfo;
import com.nilknow.classfile.constantInfo.ConstantUtf8Info;
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

    public String getUtf8(int nameIndex) {
        // 1. Get the constant pool entry at the given index
        ConstantInfo constantInfo = this.getConstantInfo(nameIndex);

        // 2. Check the type of the constantInfo object and extract the name based on the type
        switch (constantInfo.getTag()) {
            case ConstantInfo.CONSTANT_Utf8:
                return ((ConstantUtf8Info) constantInfo).getValue();  // Cast and get value
            case ConstantInfo.CONSTANT_String:
                int stringIndex = ((ConstantStringInfo) constantInfo).getStringIndex();
                return getUtf8(stringIndex);
            default:
                throw new IllegalArgumentException("Unsupported constant pool entry type: " + constantInfo.getTag());
        }
    }

    private ConstantInfo getConstantInfo(int index) {
        return this.getConstantInfos().get(index - 1);
    }
}
