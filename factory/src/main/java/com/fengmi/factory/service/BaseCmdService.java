package com.fengmi.factory.service;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.fengmi.factory.ICommandService;
import com.fengmi.factory_test_interf.SDKManager;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;
import com.fengmi.factory_test_interf.sdk_globle.TvCommandDescription;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseCmdService extends Service implements CommandSource.OnCommandListener {
    protected static final String TAG = "FactoryTest";
    protected static final int CHECK_COMMAND_RUNNING = 10000;
    protected static final int COMMAND_STARTRUN_TIMEOUT = 5000;
    protected static TvCommandDescription mTvCd;
    protected static CommandSource mCmdSource;
    private List<Command> mActivityRunningCmds = new ArrayList<>();
    protected final ICommandService.Stub mBinder = new ICommandService.Stub() {
        @Override
        public void setResult_bool(String cmdid, boolean result) throws RemoteException {
            byte[] data = new byte[1];
            if (result) {
                data[0] = 0;
            } else {
                data[0] = 1;
            }
            mCmdSource.sendMsg(cmdid, data);
        }

        @Override
        public void setResult_byte(String cmdid, byte[] resultMsg) throws RemoteException {
            mCmdSource.sendMsg(cmdid, resultMsg);
        }

        @Override
        public void setResult_string(String cmdid, String resultMsg) throws RemoteException {
            mCmdSource.sendMsg(cmdid, resultMsg.getBytes());
        }

        @Override
        public void finishCommand(String cmdid, String param) throws RemoteException {
            removeRunningCommand(cmdid, param);
        }
    };
    private Handler myHandler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == CHECK_COMMAND_RUNNING) {
                Command cmd = (Command) msg.obj;
                if (cmd != null) {
                    synchronized (mActivityRunningCmds) {
                        if (cmd.state == Command.COMMAND_STATE_INIT && mActivityRunningCmds.indexOf(cmd) >= 0) {
                            Log.w(TAG, "start run " + cmd + " timeout");
                            mActivityRunningCmds.remove(cmd);
                        }
                    }
                }
            }
            super.handleMessage(msg);
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++");
        Log.i(TAG, "FactoryTest Service Begin to Work  onCreate!");
        Log.i(TAG, "+++++++++++++++++++++++++++++++++++++++++++++++++");
        mActivityRunningCmds.clear();
        mTvCd = TvCommandDescription.getInstance();
        mCmdSource = new CommandSource(this, this);
        SDKManager.getUtilManager().closeScreenSave2Sleep();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCmdSource.finishCommandSouce();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.i(TAG, "CommandService onBind");
        String id = intent.getStringExtra(FactorySetting.EXTRA_CMDID);
        String para = intent.getStringExtra(FactorySetting.EXTRA_CMDPARA);
        synchronized (mActivityRunningCmds) {
            Command c = findRunningCommandLocked(id, para);
            if (c != null) {
                c.state = Command.COMMAND_STATE_START;
            } else {
                Log.e(TAG, "onBind can not find the pending command ");
            }
        }
        return mBinder;
    }

    protected void TvSetControlMsg(Command cmd, int para0, String para1, String para2) {
        if (cmd != null) {
            String action = TvCommandDescription.getFilterActionForCmd(cmd.cmdid);
            if (action != null) {
                Intent intent = new Intent(action);
                intent.putExtra(FactorySetting.EXTRA_BROADCAST_CMDPARA, cmd.param);
                intent.putExtra(FactorySetting.EXTRA_BROADCAST_CONTROLTYPE, para0);
                intent.putExtra(FactorySetting.EXTRA_BROADCAST_CONTROLID, para1);
                intent.putExtra(FactorySetting.EXTRA_BROADCAST_CONTROLPARA, para2);
                //LocalBroadcastManager.getInstance(this).sendBroadcast(intent);
                sendBroadcast(intent);
            }
        }
    }

    //will be called after
    protected Command removeRunningCommand(String cmdid, String param) {
        synchronized (mActivityRunningCmds) {
            Command c = findRunningCommandLocked(cmdid, param);
            if (!mActivityRunningCmds.remove(c)) {
                Log.e(TAG, "there is not pending command, something wrong? " + c);
            }
            return c;
        }
    }

    private Command findRunningCommandLocked(String id, String para) {
        for (int i = 0; i < mActivityRunningCmds.size(); i++) {
            Command c = mActivityRunningCmds.get(i);
            if (c.match(id, para)) {
                return c;
            }
        }
        return null;
    }

    //return the matched first command
    private Command findRunningCommandById(String cmdid){
        for (int i = 0; i < mActivityRunningCmds.size(); i++) {
            Command c = mActivityRunningCmds.get(i);
            if (c.cmdid.equals(cmdid)) {
                return c;
            }
        }
        return null;
    }
    protected void TvhandleCommandForActivity(Command c) {
        ComponentName component = TvCommandDescription.getComponentNameForCmd(c.cmdid);
        if (component == null) {
            Log.e(TAG, "can not run activity for " + c.cmdid);
            return;
        }
        // add task running check
        myHandler.sendMessageDelayed(myHandler.obtainMessage(CHECK_COMMAND_RUNNING, c), COMMAND_STARTRUN_TIMEOUT);
        //launch task
        Intent intent = new Intent();
        intent.setComponent(component);
        intent.putExtra(FactorySetting.EXTRA_CMDID, c.cmdid);
        intent.putExtra(FactorySetting.EXTRA_CMDPARA, c.param);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    protected Command addRunningCommand(String cmdid, String param) {
        synchronized (mActivityRunningCmds) {
            Command c = findRunningCommandLocked(cmdid, param);
            if (c != null && mActivityRunningCmds.remove(c)) {
                Log.e(TAG, "there is pending command not finish, something wrong? " + c);
            }
            c = new Command(cmdid, param);
            mActivityRunningCmds.add(c);
            return c;
        }
    }

    /* ------------- Tv Check CP210xCommand Status Start---------------*/
    protected boolean checkTvWindowStatus() {
        boolean ret = false;
        synchronized (mActivityRunningCmds) {
            for (int i = 0; i < mActivityRunningCmds.size(); i++) {
                Command c = mActivityRunningCmds.get(i);
                if (mTvCd.getCmdTypeByID(c.cmdid).equals(TvCommandDescription.CMD_TYPE_ACTIVITY_ON)) {
                    ret = true;
                    break;
                }
            }
        }
        return ret;
    }

    protected boolean checkTvWindowInOperating(String cmdid) {
        boolean ret = false;
        if (checkTvWindowStatus() && mTvCd.getCmdTypeByID(cmdid).equals(TvCommandDescription.CMD_TYPE_ACTIVITY_ON)) {
            Log.i(TAG, "[Window]checkTvWindowStatus(): " + checkTvWindowStatus());
            Log.i(TAG, "[Window]getCmdTypeByID(" + cmdid + "): " + mTvCd.getCmdTypeByID(cmdid));
            try {
                mBinder.setResult_bool(cmdid, false);
            } catch (RemoteException ex) {
                ex.printStackTrace();
            }

            ret = true;
        } else if (!checkTvWindowStatus() && mTvCd.getCmdTypeByID(cmdid).equals(TvCommandDescription.CMD_TYPE_ACTIVITY_OFF)) {
            Log.i(TAG, "[Window]checkTvWindowStatus(): " + checkTvWindowStatus());
            Log.i(TAG, "[Window]getCmdTypeByID(" + cmdid + "): " + mTvCd.getCmdTypeByID(cmdid));
            try {
                mBinder.setResult_bool(cmdid, false);
            } catch (RemoteException ex) {
                ex.printStackTrace();
                return false;
            }

            ret = true;
        }
        return ret;
    }

    protected Command getTvRunningWindCmd() {
        for (int i = 0; i < mActivityRunningCmds.size(); i++) {
            Command c = mActivityRunningCmds.get(i);
            if (mTvCd.getCmdTypeByID(c.cmdid).equals(TvCommandDescription.CMD_TYPE_ACTIVITY_ON)) {
                return c;
            }
        }
        return null;
    }
}
