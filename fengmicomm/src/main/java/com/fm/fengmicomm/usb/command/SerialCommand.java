package com.fm.fengmicomm.usb.command;

public class SerialCommand {
    private static final byte cmd_end_0 = 0x0D;
    private static final byte cmd_end_1 = 0x0A;

    private static final byte ctrl_c = 0x03;
    private static final byte ctrl_d = 0x04;
    private static final byte ctrl_e = 0x05;
    private static final byte ctrl_f = 0x06;
    private static final byte ctrl_g = 0x07;
    private static final byte ctrl_h = 0x08;

    private static byte[] exitLogcat() {
        return new byte[]{
                ctrl_c, cmd_end_0, cmd_end_1
        };
    }

    private byte[] cmdData;

    public SerialCommand() {
    }

    public static SerialCommand generateExitCMD() {
        SerialCommand sc = new SerialCommand();
        sc.cmdData = new byte[]{
                ctrl_c, cmd_end_0, cmd_end_1
        };
        return sc;
    }

    public static SerialCommand generateSerialCMD(String cmd) {
        SerialCommand sc = new SerialCommand();
        byte[] bytes = cmd.getBytes();
        int totalLen = bytes.length + 2;
        sc.cmdData = new byte[totalLen];
        System.arraycopy(bytes, 0, sc.cmdData, 0, bytes.length);
        sc.cmdData[totalLen - 2] = cmd_end_0;
        sc.cmdData[totalLen - 1] = cmd_end_1;
        return sc;
    }

    public byte[] getCmdData() {
        return cmdData;
    }

}
