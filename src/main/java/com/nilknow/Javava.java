package com.nilknow;

import com.google.common.base.Strings;


public class Javava {

    public static void main(String[] args) {
        Cmd cmd = new Cmd();
        cmd.init(args);

        if (cmd.isVersionFlag()) {
            System.out.println("version 1");
            return;
        }
        if (cmd.isHelpFlag() || Strings.isNullOrEmpty(cmd.getClazz())) {
            System.out.println("Usage: %s [-options] class [args...]");
        }
    }
}
