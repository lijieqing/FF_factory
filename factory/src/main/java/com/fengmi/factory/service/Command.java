package com.fengmi.factory.service;

import java.util.Objects;

// should use command state for timeout(bind service)
public class Command {
    public static final int COMMAND_STATE_INIT = 1;
    public static final int COMMAND_STATE_START = 2;
    public String cmdid;
    public String param;
    public int state;

    public Command(String _cmdid, String _param) {
        cmdid = _cmdid;
        param = _param;
        state = COMMAND_STATE_INIT;
    }

    //should we need command running state
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Command)) return false;
        Command other = (Command) obj;
        return match(other.cmdid, other.param);
    }

    public boolean match(String _cmdid, String _param) {
        boolean sameCmdid = Objects.equals(cmdid, _cmdid);
        boolean sameParam = Objects.equals(param, _param);
        return sameCmdid && sameParam;
    }

    public String toString() {
        return "cmdid=" + cmdid + " param=" + param;
    }
}