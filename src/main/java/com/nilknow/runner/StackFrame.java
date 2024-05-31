package com.nilknow.runner;

import com.nilknow.classfile.MethodInfo;
import com.nilknow.classfile.attributeInfo.CodeAttribute;
import com.nilknow.util.MethodInfoUtil;

public class StackFrame {
    private final int[] localVariables; // Array to hold local variables
    private final OperandStack operandStack; // Stack for operands during bytecode execution

    public StackFrame(MethodInfo methodInfo) {
        CodeAttribute codeAttribute = MethodInfoUtil.getCodeAttribute(methodInfo);
        int maxLocals = codeAttribute.getMaxLocals();
        int maxStack = codeAttribute.getMaxStack();
        this.localVariables = new int[maxLocals]; // Allocate space for local variables
        this.operandStack = new OperandStack(maxStack);
    }

    public int[] getLocalVariables() {
        return localVariables;
    }

    public void setLocalVariable(int index, int value) {
        if (index < 0 || index >= localVariables.length) {
            throw new IllegalArgumentException("Invalid local variable index");
        }
        localVariables[index] = value;
    }

    public OperandStack getOperandStack() {
        return operandStack;
    }
}
