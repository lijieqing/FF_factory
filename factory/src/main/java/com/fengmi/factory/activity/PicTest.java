package com.fengmi.factory.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;

public class PicTest extends BaseActivity {
    private LinearLayout ll_xpr;
    private FinishReceiver fR;
    private int[] bgSources = new int[]{
            R.drawable.xpr_pic,
            R.drawable.pic_xpr_1920_1080,
            R.drawable.pic_xpr_horizontal,
            R.drawable.pic_resolution,
            R.drawable.pic_4k_shadow,
            R.drawable.pic_4k_color,
            R.drawable.pic_colour,//6
            R.drawable.pic_gray_block_256,
            R.drawable.pic_gray_scale_10,
            R.drawable.pic_gray_scale_16,
            R.drawable.pic_gray_scale_32,
            R.drawable.pic_gray_scale_64,//11
            R.drawable.pic_gray_scale_128,
            R.drawable.pic_gray_scale_255,
            R.drawable.gradient_r,
            R.drawable.gradient_g,
            R.drawable.gradient_b,
            R.drawable.chessboard,
            R.drawable.reseau,
            R.drawable.pic_4_color,//19
            R.drawable.laser_appo,
            R.drawable.auto_focus//21
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fR = new FinishReceiver(this);
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.fengmi.factory_test.activity.PicTest.Finish");
        this.registerReceiver(fR, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(fR);
    }

    @Override
    protected void handleCommand(String cmdID, String cmdPara) {
        super.handleCommand(cmdID, cmdPara);
        setContentView(R.layout.pic_test);
        ll_xpr = this.findViewById(R.id.ll_xpr);
        int bg = 21;
        if (!TextUtils.isEmpty(cmdPara) && TextUtils.isDigitsOnly(cmdPara)) {
            if (bg >= bgSources.length) {
                bg = 0;
            } else {
                bg = Integer.parseInt(cmdPara);
            }
        }
        ll_xpr.setBackgroundResource(bgSources[bg]);
    }

    @Override
    protected void handleControlMsg(int cmdType, String cmdID, String cmdPara) {
        if (FactorySetting.COMMAND_TASK_STOP == cmdType) {
            finish();
            setResult(cmdID, PASS, true);
        }
    }

    private static class FinishReceiver extends BroadcastReceiver {
        private PicTest picTest;

        public FinishReceiver(PicTest picTest) {
            this.picTest = picTest;
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            boolean finish = intent.getBooleanExtra("finish", false);
            if (finish) {
                picTest.finish();
            }
        }
    }
}
