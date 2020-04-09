package com.fengmi.factory.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;
import com.fengmi.factory_test_interf.sdk_utils.SPUtil;

import java.io.File;
import java.io.IOException;

public class LocalMedia extends BaseActivity implements SurfaceHolder.Callback, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {
    private int MediaItem = 1;

    private SurfaceView mSurfaceView = null;
    private SurfaceHolder surfaceHolder = null;
    private MediaPlayer mMediaPlayer = null;

    private String[] MediaSour = new String[]{
            "/vendor/factory/autovideo_4k2k.mov",
            "/vendor/factory/autovideo_vp9.mkv",
            "/vendor/factory/autovideo_white.m2t"};

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.d(TAG, "onCompletion called");
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "Media Player onError ,what = $what, extra = " + extra);
        if (mp != null) {
            if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                Log.e(TAG, "media error, server died,we need new media player");
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();

                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setOnErrorListener(this);
                    localMediaPlay(MediaItem);
                }
            }
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.d(TAG, "MediaPlayer onPrepared ");
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            mMediaPlayer.seekTo(0);
            mMediaPlayer.setLooping(true);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated called");
        localMediaPlay(MediaItem);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        Log.d(TAG, "surfaceChanged called");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        Log.d(TAG, "surfaceDestroyed called");
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }


    @Override
    protected void handleControlMsg(int cmdType, String cmdID, String cmdPara) {
        super.handleControlMsg(cmdType, cmdID, cmdPara);
        if (FactorySetting.COMMAND_TASK_STOP == cmdType) {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            setResult(cmdID, PASS, true);
        }
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.local_media_af);

        mSurfaceView = findViewById(R.id.localmediaview);
        Log.d(TAG, "LocalMedia Started");

        LinearLayout ll_Surface = findViewById(R.id.ll_surface);
        //265 格式视频，M055 非16:9宽高播放画面会出现异常，此处进行格式设置
        ViewGroup.LayoutParams ll_param = ll_Surface.getLayoutParams();
        ll_param.height = 600;
        ll_param.width = 960;
        ll_Surface.setLayoutParams(ll_param);

        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);

        mMediaPlayer = new MediaPlayer();
        ;
        Log.v(TAG, "init MediaPlayer done");

        mMediaPlayer.setOnErrorListener(this);

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

        Log.v(TAG, "setAudioStreamType done");
        sourceDirect();
    }

    private void sourceDirect() {
        String item = (String) SPUtil.getParam(this, "factory_media_item", "1");
        Log.d(TAG, "we read factory media item is " + item);
        if (TextUtils.isDigitsOnly(item)) {
            int i = Integer.parseInt(item);
            if (i < MediaSour.length) {
                MediaItem = i;
            }
        }
        Log.d(TAG, "Factory MediaItem is " + MediaItem);
    }

    private void localMediaPlay(int item) {
        mMediaPlayer.setDisplay(surfaceHolder);
        try {
            File file = getExternalFilesDir(Environment.MEDIA_MOUNTED);
            String path = file.getAbsolutePath();
            Log.d(TAG, "source is " + path);
            Log.d(TAG, "setDataSource is " + MediaSour[item]);
            mMediaPlayer.setDataSource(MediaSour[item]);
            // mMediaPlayer.setDataSource(MediaSour[item]);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            Log.i(TAG, "IllegalArgumentException is " + e.getMessage());
        } catch (SecurityException e) {
            e.printStackTrace();
            Log.i(TAG, "SecurityException is " + e.getMessage());
        } catch (IllegalStateException e) {
            e.printStackTrace();
            Log.i(TAG, "IllegalStateException is " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            Log.i(TAG, "IOException is " + e.getMessage());
        }

        mMediaPlayer.setLooping(true);
        //设置 prepared 监听
        mMediaPlayer.setOnPreparedListener(this);
        try {
            //异步加载资源，在监听器中 start()
            mMediaPlayer.prepareAsync();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }

    }
}
