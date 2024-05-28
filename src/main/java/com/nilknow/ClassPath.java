package com.nilknow;

import com.google.common.base.Strings;
import lombok.Data;

import java.io.IOException;

@Data
public class ClassPath {
    private Entry userClassPath;

    public ClassPath(String cpOption) throws IOException {
        if (Strings.isNullOrEmpty(cpOption) ) {
            cpOption = ".";
        }
        //todo DirEntry is not enough
        this.userClassPath=new DirEntry(cpOption);
    }

    public byte[] readClass(String className) throws IOException {
        return userClassPath.readClass(className+".class");
    }
}
