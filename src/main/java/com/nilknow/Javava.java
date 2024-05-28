package com.nilknow;

import com.google.common.base.Strings;
import com.nilknow.classfile.ClassFile;
import com.nilknow.classfile.ClassFileReader;

import java.io.IOException;


public class Javava {

    public static void main(String[] args) throws IOException {
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
        System.out.println(cf);
    }
}
