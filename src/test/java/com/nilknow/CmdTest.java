package com.nilknow;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CmdTest {

    @Test
    public void testInit_parsesVersionFlag() {
        String[] args = {"-version"};
        Cmd cmd = new Cmd();
        cmd.init(args);

        assertTrue(cmd.isVersionFlag(), "Version flag should be true");
        assertFalse(cmd.isHelpFlag(), "Help flag should be false");
        assertNull(cmd.getCpOption(), "CpOption should be null");
        assertNull(cmd.getClazz(), "Clazz should be null");
        assertNull(cmd.getArgs(), "Args should be null");
    }

    @Test
    public void testInit_parsesHelpFlag() {
        String[] args = {"--help"};
        Cmd cmd = new Cmd();
        cmd.init(args);

        assertFalse(cmd.isVersionFlag(), "Version flag should be false");
        assertTrue(cmd.isHelpFlag(), "Help flag should be true");
        assertNull(cmd.getCpOption(), "CpOption should be null");
        assertNull(cmd.getClazz(), "Clazz should be null");
        assertNull(cmd.getArgs(), "Args should be null");
    }

    @Test
    public void testInit_parsesCpOption() {
        String[] args = {"-cp", ".", "ThisClass"};
        Cmd cmd = new Cmd();
        cmd.init(args);

        assertFalse(cmd.isVersionFlag(), "Version flag should be false");
        assertFalse(cmd.isHelpFlag(), "Help flag should be false");
        assertEquals(".", cmd.getCpOption(), "CpOption should be .");
        assertEquals("ThisClass", cmd.getClazz(), "Clazz should be ThisClass");
        assertNull(cmd.getArgs(), "Args should be null (empty array)");
    }

}