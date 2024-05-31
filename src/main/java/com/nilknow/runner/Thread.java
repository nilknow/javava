package com.nilknow.runner;

import com.nilknow.classfile.ConstantPool;
import com.nilknow.classfile.MethodInfo;
import com.nilknow.classfile.attributeInfo.CodeAttribute;
import com.nilknow.util.MethodInfoUtil;
import lombok.Data;

@Data
public class Thread {
    private final String name;
    private Stack stack;

    public Thread(String name) {
        this.name = name;
    }

    public interface Opcode {
        byte NOP = 0;
        byte ICONST_0 = 3;
        byte IADD = 1; // Add instruction (example)
        byte ICONST_1 = 4; // Add instruction (example)
        byte ISTORE_1 = 60;
        byte RETURN = -79;
    }

    public void runMethod(MethodInfo methodInfo, ConstantPool cp) throws Exception {
        String methodName = MethodInfoUtil.getMethodName(methodInfo, cp);
        // Check if method is static (assuming we only handle static methods for simplicity)
        if (!methodInfo.isStatic()) {
            throw new Exception("Currently only static methods are supported");
        }

        // Get the bytecode from the Code Attribute (replace with null check)
        CodeAttribute codeAttribute = MethodInfoUtil.getCodeAttribute(methodInfo);
        if (codeAttribute == null) {
            throw new Exception("Method " + methodName + " does not have a Code Attribute");
        }

        // Simulate the execution environment (replace with actual implementation)
        StackFrame frame = new StackFrame(methodInfo);
        this.stack = new Stack(1);
        this.stack.push(frame);

        // Simplified bytecode execution loop (replace with full interpretation logic)
        for (byte instruction : codeAttribute.getCode()) {
            switch (instruction) {
                case Opcode.NOP: // No operation (skip)
                    break;
                case Opcode.ICONST_1: // Push constant 1 onto the stack
                    frame.getOperandStack().push(1);
                    break;
                case Opcode.IADD: // Pop two integers from stack, add and push result
                    int operand2 = (int) frame.getOperandStack().pop();
                    int operand1 = (int) frame.getOperandStack().pop();
                    frame.getOperandStack().push(operand1 + operand2);
                    break;
                case Opcode.ISTORE_1: // Store integer on stack to local variable 1
                    frame.setLocalVariable(1, frame.getOperandStack().pop());
                case Opcode.RETURN:
                    break;
                default:
                    throw new Exception("Unsupported opcode: " + instruction);
            }
        }

        // Assuming the method is void (modify for returning values)
        if (!MethodInfoUtil.getMethodDescription(methodInfo, cp).endsWith(")V")) {
            throw new Exception("Currently only void methods are supported");
        }

        // Print the result on the stack (assuming the method returned a value)
        if (!frame.getOperandStack().isEmpty()) {
            System.out.println("Method result (on stack): " + frame.getOperandStack().pop());
        }
    }
}
