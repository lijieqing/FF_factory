package com.fengmi.factory.activity;

import android.content.Context;
import android.media.tv.TvContentRating;
import android.media.tv.TvInputInfo;
import android.media.tv.TvInputManager;
import android.media.tv.TvTrackInfo;
import android.media.tv.TvView;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;


import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;

import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

public class InputSourceForTvView extends BaseActivity {
    private Source mCurrentSource = Source.UNKNOWN;
    private Source mTargetSource = Source.HDMI1;
    private Handler mHandler = new Handler();
    private int SourceWaitCounts = 0;
    private String CurrentCmdId = null;
    //------------------------init-------------------------------------
    private TvInputManager mTvInputManager;
    private TvView mTvView;
    private TvViewCallback mTvViewCallback = null;
    private ManagerCallBack mManagerCallBack = null;
    private List<TvInputInfo> mAvailableSourceList = null;
    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            Log.i(TAG, "=======" + SourceWaitCounts + "==========");
            if (SourceWaitCounts < 20) {
                Source source = getCurrentSource();
                SourceWaitCounts++;
                // TODO Auto-generated method stub
                //要做的事情，这里再次调用此Runnable对象，以实现每两秒实现一次的定时器操作
                Log.i(TAG, "mCurrentSource is " + mCurrentSource);
                if (source != mTargetSource) {
                    mHandler.postDelayed(this, 1200);
                } else {
                    updateAvailableSource();
                    setResult(CurrentCmdId, true, false);
                    mHandler.removeCallbacks(runnable);
                }
            } else {
                SourceWaitCounts = 0;
                Log.i(TAG, "timeout, cancel SourceWaitCounts");
                setResult(CurrentCmdId, false, false);
                mHandler.removeCallbacks(runnable);
            }
        }
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i(TAG, "[onCreate] set to activity prepare");
        requestWindowFeature(android.view.Window.FEATURE_NO_TITLE | android.view.Window.FEATURE_OPTIONS_PANEL);
        setContentView(R.layout.input_source_for_tv_view);
        initView();
        initEnv();
    }

    void initView() {
        mTvView = (TvView) findViewById(R.id.tranplentview);
        mTvView.setVisibility(View.VISIBLE);
        //mOutPutView = (TextView) findViewById(R.id.event_output);
    }


    protected void onResume() {
        super.onResume();
    }

    protected void onPause() {
        super.onPause();
    }

    protected void onDestroy() {
        super.onDestroy();
    }

    public boolean dispatchKeyEvent(KeyEvent event) {
        int keyCode = event.getKeyCode();
        if ((keyCode == KeyEvent.KEYCODE_BACK) || (keyCode == KeyEvent.KEYCODE_DPAD_RIGHT) || (keyCode == KeyEvent.KEYCODE_DPAD_LEFT)
                || (keyCode == KeyEvent.KEYCODE_DPAD_UP) || (keyCode == KeyEvent.KEYCODE_DPAD_DOWN)) {
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    public void handleCommand(String cmdid, String param) {
        int setTime;
        super.handleCommand(cmdid, param);
        Log.i(TAG, "handleCommand param is :[" + param + "]");
    }

    public void handleControlMsg(int cmdtype, String cmdid, String cmdpara) {
        int setTime;
        Log.e(TAG, "input id: " + cmdid);
        Log.e(TAG, "input information: " + cmdpara);
        if (FactorySetting.COMMAND_TASK_STOP == cmdtype) {
            setResult(cmdid, "noresponse", true);
        } else if (FactorySetting.COMMAND_TASK_BUSINESS == cmdtype) {
            Source currSource = getCurrentSource();
            String[] paraInput;
            if (cmdpara == null) {
                setResult(cmdid, false, false);
                Log.e(TAG, "no input information");
                return;
            }
            paraInput = cmdpara.split(":");
            if (paraInput.length < 2) {
                setResult(cmdid, false, false);
                Log.i(TAG, cmdpara + " --- cmd para isn't enough");
                return;
            }
            Log.i(TAG, "[0]:" + paraInput[0] + "---[1]" + paraInput[1]);
            if (paraInput[0].equals("SOURCE")) {
                Source id = getSourceByCmd(paraInput[1]);
                if (id == Source.UNKNOWN) {
                    setResult(cmdid, false, false);
                    Log.i(TAG, "set source Id error: " + cmdpara);
                }
                if (currSource != id) {
                    TvInputInfo info = switchSource(id);
                    if (info == null) {
                        setResult(cmdid, false, false);
                        return;
                    }
                    mTargetSource = id;
                    SourceWaitCounts = 0;
                    CurrentCmdId = cmdid;
                    mHandler.postDelayed(runnable, 200);
                } else {
                    switchSource(currSource);
                    Log.i(TAG, "switch into current source");
                    setResult(cmdid, true, false);
                }
            } else if (paraInput[0].equals("CHANNEL")) {
                Log.e(TAG, "can't set channel at current source: " + paraInput[1]);
                setResult(cmdid, false, false);
            } else {
                Log.e(TAG, "tag error: " + paraInput[0]);
                setResult(cmdid, false, false);
            }
        }
    }

    private TvInputInfo getTvInputInfo(Source s) {
        if (mAvailableSourceList == null) {
            return null;
        }
        for (TvInputInfo info : mAvailableSourceList) {
            String hwID = infoIDSplit(info.getId());
            if (hwID.equals(s.sourceName)) {
                return info;
            }
        }
        return null;
    }
    private String infoIDSplit(String id){
        String[] ids = id.split("/");
        Log.d(TAG, "TvInputInfo : "+Arrays.toString(ids));
        if (ids.length == 3){
            return ids[2];
        }else {
            return id;
        }
    }

    private TvInputInfo switchSource(Source source) {
        updateAvailableSource();
        TvInputInfo info = getTvInputInfo(source);
        if (info != null) {
            Uri parse = Uri.parse("content://android.media.tv/passthrough/" + URLEncoder.encode(info.getId()));
            Log.i(TAG, "switchSource:: uri:" + parse);
            mTvView.tune(info.getId(), parse);
        }
        return info;
    }

    private Source getCurrentSource() {
        return mCurrentSource;
    }

    public Source getSourceByName(String name) {
        if (TextUtils.isEmpty(name)) {
            return Source.UNKNOWN;
        }
        Source[] sources = Source.values();
        for (int i = 0; i < sources.length; i++) {
            if (name.contains(sources[i].sourceName)) {
                return sources[i];
            }
        }
        return Source.UNKNOWN;
    }

    public Source getSourceByCmd(String aCmd) {
        Source[] sources = Source.values();
        for (int i = 0; i < sources.length; i++) {
            if (sources[i].cmdName.equals(aCmd)) {
                return sources[i];
            }
        }
        return Source.UNKNOWN;
    }

    private void initEnv() {
        mTvViewCallback = new TvViewCallback();
        mManagerCallBack = new ManagerCallBack();
        mTvInputManager = (TvInputManager) getSystemService(Context.TV_INPUT_SERVICE);
        mTvInputManager.registerCallback(mManagerCallBack, mHandler);
        mTvView.setCallback(mTvViewCallback);
    }

    private void updateAvailableSource() {
        List<TvInputInfo> tvInputList = mTvInputManager.getTvInputList();
        Log.i(TAG, "inputSource size:" + tvInputList.size());
        for (TvInputInfo info : tvInputList) {
            Log.i(TAG, "inputSource:" + info.getId());
        }
        mAvailableSourceList = tvInputList;
    }

    /*
	Hdmi1: HW5
	Hdmi2: HW6
	AV1: HW1
	Hdmi3: HW7
    */
    public enum Source {
        HDMI1(1, "HW5", "hd1"), HDMI2(2, "HW6", "hd2"), HDMI3(3, "HW7", "hd3"), CVBS(10, "HW1", "cvb"), UNKNOWN(-1, "UNKNOWN", "UNKNOWN");
        int id;
        String sourceName;
        String cmdName;

        Source(int aId, String aName, String aCmdName) {
            id = aId;
            sourceName = aName;
            cmdName = aCmdName;
        }
    }

//============================Callback======================================================

    class ManagerCallBack extends TvInputManager.TvInputCallback {

        @Override
        public void onInputStateChanged(String inputId, int state) {
            Log.i(TAG, "Manager.onInputStateChanged:: inputId:" + inputId + ",state:" + state);
            Source s = getSourceByName(inputId);
            if (state == TvInputManager.INPUT_STATE_CONNECTED && s == mTargetSource) {
                switchSource(s);
            }
        }

        @Override
        public void onInputAdded(String inputId) {
            Log.i(TAG, "Manager.onInputAdded:: inputId:" + inputId);
            //updateAvailableSource();
        }

        @Override
        public void onInputRemoved(String inputId) {
            Log.i(TAG, "Manager.onInputRemoved:: inputId:" + inputId);
            Source s = getSourceByName(inputId);
            if (s != Source.UNKNOWN && s == mCurrentSource) {
                mCurrentSource = Source.UNKNOWN;
            }
        }

        @Override
        public void onInputUpdated(String inputId) {
            Log.i(TAG, "Manager.onInputUpdated:: inputId:" + inputId);
            updateAvailableSource();
        }

        @Override
        public void onTvInputInfoUpdated(TvInputInfo inputInfo) {
            Log.i(TAG, "Manager.onTvInputInfoUpdated:: inputInfo:" + inputInfo);
            updateAvailableSource();
        }
    }

    class TvViewCallback extends TvView.TvInputCallback {
        @Override
        public void onConnectionFailed(String inputId) {
            Log.i(TAG, "onConnectionFailed:: inputId:" + inputId);
        }

        @Override
        public void onDisconnected(String inputId) {
            Log.i(TAG, "onDisconnected:: inputId:" + inputId);
        }

        @Override
        public void onChannelRetuned(String inputId, Uri channelUri) {
            Log.i(TAG, "onChannelRetuned:: inputId:" + inputId + ",channelUri:" + channelUri);
        }

        @Override
        public void onTracksChanged(String inputId, List<TvTrackInfo> tracks) {
            Log.i(TAG, "onTracksChanged:: inputId:" + inputId);
        }

        @Override
        public void onTrackSelected(String inputId, int type, String trackId) {
            Log.i(TAG, "onTrackSelected:: inputId:" + inputId + ",type:" + type + ",trackId:" + trackId);
        }

        @Override
        public void onVideoSizeChanged(String inputId, int width, int height) {
            Log.i(TAG, "onVideoSizeChanged:: inputId:" + inputId);
        }

        @Override
        //@UsedByHdmi
        public void onVideoAvailable(String inputId) {
            Source s = getSourceByName(inputId);
            Log.i(TAG, "onVideoAvailable:: inputId:" + inputId + ",source:" + s);
            if (s != Source.UNKNOWN) {
                mCurrentSource = s;
            }
        }

        @Override
        //@UsedByHdmi
        public void onVideoUnavailable(String inputId, int reason) {
            Log.i(TAG, "onVideoUnavailable:: inputId:" + inputId);
            Source s = getSourceByName(inputId);
            if (s != Source.UNKNOWN && s == mCurrentSource) {
                mCurrentSource = Source.UNKNOWN;
            }
        }

        @Override
        public void onContentAllowed(String inputId) {
            Log.i(TAG, "onContentAllowed:: inputId:" + inputId);
        }

        @Override
        public void onContentBlocked(String inputId, TvContentRating rating) {
            Log.i(TAG, "onContentBlocked:: inputId:" + inputId + ",rating:" + rating);
        }

        @Override
        public void onTimeShiftStatusChanged(String inputId, int status) {
            Log.i(TAG, "onTimeShiftStatusChanged:: inputId:" + inputId + ",status:" + status);
        }
    }


}
