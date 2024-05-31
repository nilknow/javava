package com.nilknow;

import com.google.common.base.Strings;
import com.nilknow.classfile.ClassFile;
import com.nilknow.classfile.ClassFileReader;
import com.nilknow.classfile.MethodInfo;
import com.nilknow.runner.Thread;
import com.nilknow.util.MethodInfoUtil;


public class Javava {

    public static void main(String[] args) throws Exception {
        Cmd cmd = new Cmd();
        cmd.init(args);

        if (cmd.isVersionFlag()) {
            System.out.println("version 1");
            return;
        }
        if (cmd.isHelpFlag() || Strings.isNullOrEmpty(cmd.getClazz())) {
            System.out.println("Usage: Javava [-options] class [args...]");
            return;
        }

        System.out.printf("classpath:%s class:%s \n", cmd.getCpOption(), cmd.getClazz());
        ClassPath cp = new ClassPath(cmd.getCpOption());
        byte[] bytes = cp.readClass(cmd.getClazz());
        ClassFileReader reader = new ClassFileReader(new ClassByteReader(bytes));
        ClassFile cf = reader.read();

        MethodInfo[] methods = cf.getMethods();
        for (MethodInfo methodInfo : methods) {
            if ("main".equals(MethodInfoUtil.getMethodName(methodInfo,cf.getConstantPool())) &&
                    "([Ljava/lang/String;)V".equals(MethodInfoUtil.getMethodDescription(methodInfo,cf.getConstantPool()))) {
                Thread mainThread = new Thread("main");
                mainThread.runMethod(methodInfo, cf.getConstantPool());
            }
        }
    }
}
