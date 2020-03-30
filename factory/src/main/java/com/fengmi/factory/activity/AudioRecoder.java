package com.fengmi.factory.activity;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.TextView;


import com.fengmi.factory.R;
import com.fengmi.factory_test_interf.sdk_globle.FactorySetting;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

public class AudioRecoder extends BaseActivity implements
        SurfaceHolder.Callback, MediaPlayer.OnCompletionListener,
        MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener {

    private SurfaceView mSurfaceView;
    private SurfaceHolder surfaceHolder;
    private MediaPlayer mMediaPlayer;
    private TextView tvNoUSB;
    private TextView tvUSBPlaying;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.audio_recoder);

        mSurfaceView = findViewById(R.id.sfv_u_video);
        tvNoUSB = findViewById(R.id.tv_no_usb_video);
        tvUSBPlaying = findViewById(R.id.tv_usb_video);

        Log.d(TAG, "U Disk Media Play Activity onCreate");

        surfaceHolder = mSurfaceView.getHolder();
        surfaceHolder.addCallback(this);

        mMediaPlayer = new MediaPlayer();

        Log.v(TAG, "init MediaPlayer done");

        mMediaPlayer.setOnErrorListener(this);

        mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);


        Log.v(TAG, "setAudioStreamType done");
    }





    private void usbDiskMediaPlay() {

        mMediaPlayer.setDisplay(surfaceHolder);
        try {
            File file = getExternalFilesDir(Environment.MEDIA_MOUNTED);
            String path = file.getAbsolutePath();
            Log.d(TAG, "source is " + path);
            Log.i(TAG, "setDataSource is " + file.getAbsolutePath());
            String sourcePath = getUSBMediaSourcePath();
            if (sourcePath == null) {
                tvNoUSB.setVisibility(View.VISIBLE);
                tvUSBPlaying.setVisibility(View.GONE);
                return;
            } else {
                tvUSBPlaying.setVisibility(View.VISIBLE);
                tvNoUSB.setVisibility(View.GONE);

                mMediaPlayer.setDataSource(sourcePath);
            }
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i(TAG, "IllegalArgumentException is " + e.getMessage());
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i(TAG, "SecurityException is " + e.getMessage());
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i(TAG, "IllegalStateException is " + e.getMessage());
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.v(TAG, "surfaceChanged called");
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.v(TAG, "surfaceDestroyed called");
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        Log.v(TAG, "surfaceCreated called");
        usbDiskMediaPlay();
    }

    @Override
    public void onCompletion(MediaPlayer Player) {

    }

    @Override
    public void handleCommand(String cmdid, String param) {
        int i = 0;
        super.handleCommand(cmdid, param);
    }


    @Override
    public void handleControlMsg(int cmdtype, String cmdid, String cmdpara) {
        Log.i(TAG, "handle windows control message");
        if (FactorySetting.COMMAND_TASK_STOP == cmdtype) {
            if (mMediaPlayer != null) {
                mMediaPlayer.stop();
                mMediaPlayer.release();
                mMediaPlayer = null;
            }

            setResult(cmdid, PASS, true);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e(TAG, "Media Player onError ,what = " + what + ", extra = " + extra);
        if (mp != null) {
            if (what == MediaPlayer.MEDIA_ERROR_SERVER_DIED) {
                Log.e(TAG, "media error, server died,we need new media player");
                if (mMediaPlayer != null) {
                    mMediaPlayer.stop();
                    mMediaPlayer.release();

                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    mMediaPlayer.setOnErrorListener(this);
                    usbDiskMediaPlay();
                }
            }
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        Log.e(TAG, "MediaPlayer onPrepared ");
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            mMediaPlayer.seekTo(0);
            mMediaPlayer.setLooping(true);
        }
    }

    /**
     * 获取 U 盘内的视频文件路径
     *
     * @return file path
     */
    private String getUSBMediaSourcePath() {
        String root = "/mnt/media_rw";
        File rootFile = new File(root);
        if (rootFile.exists()) {
            String[] usbList = rootFile.list();
            Log.e(TAG, "usbList " + Arrays.toString(usbList));
            if (usbList.length == 1) {
                root = root + "/" + usbList[0];
                File usbPath = new File(root);
                if (usbPath.exists()) {
                    String[] dataFiles = usbPath.list();
                    Log.e(TAG, "dataFiles " + Arrays.toString(dataFiles));
                    for (String dataFile : dataFiles) {
                        if (dataFile.contains("factory")) {
                            return root + "/" + dataFile;
                        }
                    }
                }
            }
        }
        return null;
    }

}
