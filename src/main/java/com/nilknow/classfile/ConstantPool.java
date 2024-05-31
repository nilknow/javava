package com.nilknow.classfile;

import com.nilknow.classfile.constantInfo.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ConstantPool {
    private List<ConstantInfo> constantInfos;

    public ConstantPool(int size) {
        this.constantInfos = new ArrayList<>(size);
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

    public ConstantInfo getConstantInfo(int index) {
        return this.getConstantInfos().get(index - 1);
    }

    public String getClassName(int classIndex) {
        ConstantClassInfo classInfo = (ConstantClassInfo) this.getConstantInfo(classIndex);
        return this.getUtf8(classInfo.getNameIndex());
    }

    public ConstantNameAndTypeInfo getNameAndType(int nameAndTypeIndex) {
        return (ConstantNameAndTypeInfo) this.getConstantInfo(nameAndTypeIndex);
    }

    public String[] getParameterTypes(int descriptorIndex) {
        String descriptor = this.getUtf8(descriptorIndex);  // Assuming String type for descriptor
        if (descriptor == null) {
            return new String[0];  // Empty array for no parameters
        }

        List<String> parameterTypes = new ArrayList<>();
        int startIndex = descriptor.indexOf('(');  // Find opening parenthesis (start of parameter list)
        if (startIndex == -1) {
            return new String[0];  // No parameters if no opening parenthesis
        }

        int endIndex = descriptor.indexOf(')');  // Find closing parenthesis (end of parameter list)
        if (endIndex == -1) {
            throw new RuntimeException("Invalid method descriptor: " + descriptor);  // Handle malformed descriptor
        }

        String parameterList = descriptor.substring(startIndex + 1, endIndex);  // Extract parameter list

        // Split parameter list based on type separators
        String[] parameterTypeArray = parameterList.split(",");
        for (String type : parameterTypeArray) {
            parameterTypes.add(parseParameterType(type));  // Parse individual parameter type
        }

        return parameterTypes.toArray(new String[0]);
    }

    private String parseParameterType(String type) {
        if (type.startsWith("L")) {  // Class type
            return type.substring(1, type.length() - 1).replace('/', '.');  // Convert internal class name to full name
        } else {
            // Handle primitive types based on their character codes (B, C, S, I, etc.)
            return convertPrimitiveType(type.charAt(0));  // Replace with your logic to convert primitive type codes
        }
    }

    private String convertPrimitiveType(char code) {
        switch (code) {
            case 'B': return "byte";
            case 'C': return "char";
            case 'S': return "short";
            case 'I': return "int";
            // ... (handle other primitive types)
            default: return null;  // Handle unknown type code
        }
    }
}
