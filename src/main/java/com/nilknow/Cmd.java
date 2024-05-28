package com.nilknow;

import lombok.Data;

@Data
public class Cmd {
    private boolean helpFlag;
    private boolean versionFlag;
    private String cpOption;
    private String clazz;
    private String[] args;

    public void init(String[] args){
        for (int i = 0; i < args.length; i++) {
            if ("-version".equals(args[i]) || "--version".equals(args[i])) {
                this.setVersionFlag(true);
            } else if ("-h".equals(args[i]) || "--help".equals(args[i])) {
                this.setHelpFlag(true);
            } else if ("-cp".equals(args[i])) {
                this.setCpOption(args[++i]);
            } else {
                this.setClazz(args[i]);
            }
        }
    }
}
