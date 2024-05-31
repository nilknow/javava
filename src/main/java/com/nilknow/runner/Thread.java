package com.nilknow.runner;

import com.nilknow.classfile.ConstantPool;
import com.nilknow.classfile.MethodInfo;
import com.nilknow.classfile.attributeInfo.CodeAttribute;
import com.nilknow.classfile.constantInfo.ConstantFieldRefInfo;
import com.nilknow.classfile.constantInfo.ConstantMethodRefInfo;
import com.nilknow.classfile.constantInfo.ConstantNameAndTypeInfo;
import com.nilknow.util.MethodInfoUtil;
import lombok.Data;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

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
        byte ICONST_1 = 4; // Add instruction (example)
        byte LDC = 18;
        byte ILOAD_1 = 27;
        byte ILOAD_2 = 28;
        byte ALOAD_1 = 43;
        byte ASTORE = 58;
        byte ISTORE_1 = 60;
        byte ISTORE_2 = 61;
        byte ISTORE_3 = 62;
        byte ASTORE_1 = 76;
        byte IADD = 96; // Add instruction (example)
        byte GETSTATIC = (byte) 178;
        byte INVOKEVIRTUAL = (byte) 182;
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
        for (int i = 0; i < codeAttribute.getCode().length; i++) {
            byte instruction = codeAttribute.getCode()[i];
            switch (instruction) {
                case Opcode.NOP: // No operation (skip)
                    break;
                case Opcode.ICONST_0:
                    frame.getOperandStack().push(0);
                    break;
                case Opcode.ICONST_1: // Push constant 1 onto the stack
                    frame.getOperandStack().push(1);
                    break;
                case Opcode.LDC: // Push String constant onto stack
                    int opVal = codeAttribute.getCode()[++i];
                    String str = cp.getUtf8(opVal);
                    frame.getOperandStack().push(str);
                    break;
                case Opcode.ILOAD_1:  // Load integer from local variable 1 onto stack
                    frame.getOperandStack().push(frame.getLocalVariables()[1]);
                    break;
                case Opcode.ILOAD_2:
                    frame.getOperandStack().push(frame.getLocalVariables()[2]);
                    break;
                case Opcode.ALOAD_1:
                    frame.getOperandStack().push(frame.getLocalVariables()[1]);
                    break;
                case Opcode.ASTORE:
                    int index = codeAttribute.getCode()[++i];
                    frame.setLocalVariable(index, frame.getOperandStack().pop());
                    break;
                case Opcode.IADD: // Pop two integers from stack, add and push result
                    int operand2 = (int) frame.getOperandStack().pop();
                    int operand1 = (int) frame.getOperandStack().pop();
                    frame.getOperandStack().push(operand1 + operand2);
                    break;
                case Opcode.ISTORE_1: // Store integer on stack to local variable 1
                    frame.setLocalVariable(1, (int) frame.getOperandStack().pop());
                    break;
                case Opcode.ISTORE_2:
                    frame.setLocalVariable(2, (int) frame.getOperandStack().pop());
                    break;
                case Opcode.ISTORE_3:
                    frame.setLocalVariable(3, (int) frame.getOperandStack().pop());
                    break;
                case Opcode.ASTORE_1:
                    frame.setLocalVariable(1, frame.getOperandStack().pop());
                    break;
                case Opcode.GETSTATIC:
                    i = getStatic(cp, codeAttribute, i, frame);
                    break;
                case Opcode.INVOKEVIRTUAL:
                    i = invokeVirtual(cp, codeAttribute, i, frame);
                    break;
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

    private int invokeVirtual(ConstantPool cp, CodeAttribute codeAttribute, int i, StackFrame frame) throws InvocationTargetException, IllegalAccessException, ClassNotFoundException, NoSuchMethodException {
        int methodIndex1 = codeAttribute.getCode()[++i];
        int methodIndex2 = codeAttribute.getCode()[++i];
        int methodIndex = (methodIndex1 << 8) | methodIndex2;
        ConstantMethodRefInfo methodRefInfo = (ConstantMethodRefInfo) cp.getConstantInfo(methodIndex);

        // Resolve class, method name, and parameter types
        String className = cp.getClassName(methodRefInfo.getClassIndex());
        ConstantNameAndTypeInfo methodNameAndType = cp.getNameAndType(methodRefInfo.getNameAndTypeIndex());
        String methodName = cp.getUtf8(methodNameAndType.getNameIndex());
        String[] parameterTypes = cp.getParameterTypes(methodNameAndType.getDescriptorIndex());

        // Get object reference from operand stack (assuming instance method)
        Object param = frame.getOperandStack().pop();

        // Get the method object based on class, method name, and parameter types (replace with your mechanism)
        Method method = this.getClass().getClassLoader().loadClass(className.replace("/","."))
                .getDeclaredMethod(methodName, getClassesFromTypeNames(parameterTypes));

        // Prepare method arguments from operand stack based on parameter types
        Object caller = frame.getOperandStack().pop();

        // Invoke the virtual method
        method.setAccessible(true);  // Might be needed depending on access modifiers
        Object result = method.invoke(caller,param);  // Pass arguments as array

        // Handle return value based on method return type (optional)
        if (!method.getReturnType().equals(void.class)) {
            frame.getOperandStack().push(result);  // Push non-void return value
        }
        return i;
    }

    private Class<?>[] getClassesFromTypeNames(String[] parameterTypes) throws ClassNotFoundException {
        Class<?>[] classes = new Class<?>[parameterTypes.length];
        for (int i = 0; i < parameterTypes.length; i++) {
            classes[i] = convertTypeNameToClass(parameterTypes[i]);
        }
        return classes;
    }

    private Class<?> convertTypeNameToClass(String typeName) throws ClassNotFoundException {
        if (typeName.equals("void")) {
            return void.class;  // Handle void return type
        } else if (typeName.startsWith("[")) {
            return convertArrayTypeNameToClass(typeName);  // Handle array types
        } else {
            return Class.forName(typeName.replace('/', '.'));  // Replace '/' with '.' for class names
        }
    }

    private Class<?> convertArrayTypeNameToClass(String typeName) throws ClassNotFoundException {
        int dimension = 0;
        while (typeName.startsWith("[")) {
            dimension++;
            typeName = typeName.substring(1);
        }
        Class<?> baseType = convertTypeNameToClass(typeName);
        return createArrayType(baseType, dimension);
    }

    private static Class<?> createArrayType(Class<?> baseType, int dimension) throws ClassNotFoundException {
        String arrayClassName = "";
        for (int i = 0; i < dimension; i++) {
            arrayClassName += "[";
        }
        arrayClassName += baseType.getName();
        return Class.forName(arrayClassName);
    }

    private int getStatic(ConstantPool cp, CodeAttribute codeAttribute, int i, StackFrame frame) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        int fieldIndex1 = codeAttribute.getCode()[++i];
        int fieldIndex2 = codeAttribute.getCode()[++i];
        int fieldIndex = (fieldIndex1 << 8) | fieldIndex2;
        ConstantFieldRefInfo fieldRefInfo = (ConstantFieldRefInfo) cp.getConstantInfo(fieldIndex);

        // Resolve class and field name
        String className = cp.getClassName(fieldRefInfo.getClassIndex());
        ConstantNameAndTypeInfo fieldNameAndType = cp.getNameAndType(fieldRefInfo.getNameAndTypeIndex());
        String fieldName = cp.getUtf8(fieldNameAndType.getNameIndex());

        // Get the field object based on class and field name (replace with your mechanism)
        Field field = this.getClass().getClassLoader().loadClass(className.replace("/",".")).getDeclaredField(fieldName);

        // Handle static field access
        Object objectRef = null;  // Assuming a static field doesn't require an object reference
        field.setAccessible(true);  // Might be needed depending on access modifiers
        Object fieldValue = field.get(objectRef);  // Access the static field value

        // Push the retrieved field value onto the operand stack
        frame.getOperandStack().push(fieldValue);
        return i;
    }
}
