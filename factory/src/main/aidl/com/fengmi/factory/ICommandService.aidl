// ICommandService.aidl
package com.fengmi.factory;

interface ICommandService {
    void setResult_bool(String cmdid, boolean result);
    void setResult_byte(String cmdid, in byte[] resultMsg);
    void setResult_string(String cmdid, String resultMsg);
    void finishCommand(String cmdid, String param);
}
