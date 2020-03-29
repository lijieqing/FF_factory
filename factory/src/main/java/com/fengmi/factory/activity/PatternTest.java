package com.fengmi.factory.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;

import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;
import com.fengmi.factory_test_interf.sdk_globle.TvCommandDescription;


public class PatternTest extends BaseActivity {
    private ImageView ivPattern;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pattern_test);
        ivPattern = findViewById(R.id.iv_pattern);
    }

    @Override
    public void handleControlMsg(int cmdtype, String cmdid, String cmdpara) {
        if (cmdtype == FactorySetting.COMMAND_TASK_BUSINESS) {
            if (cmdid.equals(Integer.toHexString(TvCommandDescription.CMDID_PATTERN_SWITCH).toUpperCase())) {
                if (cmdpara.matches("[0-9]{1,3}:[0-9]{1,3}:[0-9]{1,3}")) {
                    String[] rgb = cmdpara.split(":");
                    int red = Integer.parseInt(rgb[0]);
                    int green = Integer.parseInt(rgb[1]);
                    int blue = Integer.parseInt(rgb[2]);

                    ivPattern.setBackgroundColor(Color.rgb(red, green, blue));
                    setResult(cmdid, true, false);
                } else {
                    setResult(cmdid, false, false);
                }
            }

        }
        if (cmdtype == FactorySetting.COMMAND_TASK_STOP) {
            setResult(cmdid, true);
        }

    }

    @Override
    public void handleCommand(String cmdid, String param) {
        super.handleCommand(cmdid, param);
    }
}
