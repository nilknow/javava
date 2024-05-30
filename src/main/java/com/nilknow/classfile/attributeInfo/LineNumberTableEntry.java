package com.nilknow.classfile.attributeInfo;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LineNumberTableEntry {
    private int startPC;
    private int lineNumber;
}