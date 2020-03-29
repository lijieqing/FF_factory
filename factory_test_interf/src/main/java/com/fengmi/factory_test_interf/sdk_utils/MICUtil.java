package com.fengmi.factory_test_interf.sdk_utils;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.text.TextUtils;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static com.fengmi.factory_test_interf.sdk_interf.BaseMiddleware.TAG;

public final class MICUtil {
    private static AudioRecord audioRecord = null;
    private static volatile boolean isRecording = false;
    private static volatile AtomicInteger duration;

    private static Timer countTimer;
    /**
     * 录音数队列
     */
    private static ConcurrentLinkedQueue<byte[]> audioQueue = new ConcurrentLinkedQueue<byte[]>();
    private static ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(2, 2, 60, TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>());

    /*默认数据*/
    private static int mSampleRateInHZ = 48000; //采样率
    private static int mAudioFormat = AudioFormat.ENCODING_PCM_16BIT;  //位数
    private static int mChannelConfig = AudioFormat.CHANNEL_IN_MONO;   //声道

    private static int mRecordBufferSize;
    private static byte[] mAudioData;
    private static int source;

    /**
     * 通过 tinycap 来录音
     *
     * @param longTime 单位：秒
     * @return true or false
     */
    public static boolean startTinyCap(String longTime) {
        if (TextUtils.isEmpty(longTime)) {
            longTime = "10";
        }
        if (!TextUtils.isDigitsOnly(longTime)) {
            return false;
        } else {
            final String capCMD = "/system/bin/tinycap /data/factory.wav -D 0 -d 7 -c 6 -r 16000 -b 16 -T " + longTime;
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ShellUtil.execCommand(capCMD);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        }
    }

    /**
     * 播放录音文件
     *
     * @return true or false
     */
    public static boolean startTinyPlay() {
        File wav = new File("/data/factory.wav");
        if (wav.exists()) {
            final String playCMD = "/system/bin/tinyplay /data/factory.wav ";
            mExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        ShellUtil.execCommand(playCMD);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
            return true;
        } else {
            return false;
        }
    }

    public static byte[] readAudioFile() {
        byte[] data = new byte[]{0, 0, 0, 0};
        File wav = new File("/data/factory.wav");
        if (wav.exists()) {
            try {
                FileInputStream is = new FileInputStream(wav);
                int len = is.available();
                Log.d(TAG, "/data/factory.wav file length is " + len);
                if (len > 0) {
                    data = new byte[len];
                    is.read(data);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Log.d(TAG, "/data/factory.wav file is not exist");
        }

        return data;
    }

    public static boolean startAudioRecord(String longTime, String type) {
        switch (type) {
            case "0":
                source = MediaRecorder.AudioSource.DEFAULT;
                break;
            case "1":
                source = MediaRecorder.AudioSource.CAMCORDER;
                break;
            case "2":
                source = MediaRecorder.AudioSource.REMOTE_SUBMIX;
                break;
            case "3":
                source = MediaRecorder.AudioSource.UNPROCESSED;
                break;
            case "4":
                source = MediaRecorder.AudioSource.VOICE_CALL;
                break;
            case "5":
                source = MediaRecorder.AudioSource.VOICE_COMMUNICATION;
                break;
            case "6":
                source = MediaRecorder.AudioSource.VOICE_DOWNLINK;
                break;
            case "7":
                source = MediaRecorder.AudioSource.VOICE_RECOGNITION;
                break;
            case "8":
                source = MediaRecorder.AudioSource.VOICE_UPLINK;
                break;
        }
        if (TextUtils.isDigitsOnly(longTime)) {
            duration = new AtomicInteger(Integer.parseInt(longTime));
            init();
            return true;
        } else {
            return false;
        }
    }

    private static void init() {
        mRecordBufferSize = AudioRecord.getMinBufferSize(mSampleRateInHZ, mChannelConfig, mAudioFormat);
        if (audioRecord == null) {
            audioRecord = new AudioRecord(
                    source,
                    mSampleRateInHZ,
                    mChannelConfig,
                    mAudioFormat,
                    mRecordBufferSize
            );

            if (audioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                audioRecord = null;
                mRecordBufferSize = 0;
            } else {
                if (isRecording) {
                    Log.d(TAG, "already in recording , please wait ---------");
                    return;
                }
                if (countTimer == null) {
                    countTimer = new Timer();
                    countTimer.schedule(new TimerTask() {
                        @Override
                        public void run() {
                            duration.decrementAndGet();
                        }
                    }, 50, 1000);
                }
                mAudioData = new byte[320];
                startRecording();
            }
        }
    }

    private static void startRecording() {
        String tempName = System.currentTimeMillis() + "_" + mSampleRateInHZ;
        final File tmpFile = createFile(tempName + ".pcm");
        final File tmpOutFile = createFile(tempName + ".wav");
        if (tmpFile == null || tmpOutFile == null) {
            Log.d(TAG, "audio temp file is null");
            return;
        }
        Log.d(TAG, "audio temp file path is " + tmpFile.getAbsolutePath());

        isRecording = true;
        audioRecord.startRecording();

        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                try {
                    FileOutputStream outputStream = new FileOutputStream(tmpFile.getAbsoluteFile());

                    while (isRecording && duration.get() > 0) {
                        int readSize = audioRecord.read(mAudioData, 0, mAudioData.length);
                        Log.d(TAG, "we read audio data size is " + readSize);

                        Log.d(TAG, "we read audio data is \n" + Arrays.toString(mAudioData));

                        outputStream.write(mAudioData);
                    }

                    outputStream.close();

                    pcmToWave(tmpFile.getAbsolutePath(), tmpOutFile.getAbsolutePath());

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Log.d(TAG, " Error ：FileNotFoundException ");
                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d(TAG, " Error ：IOException " + e.getMessage());
                }

                isRecording = false;
            }
        });
    }

    private static File createFile(String name) {
        String filePath = "/data/factory/";
        File direct = new File(filePath);
        if (!direct.exists()) {
            direct.mkdirs();
        }
        File objFile = new File(filePath + name);
        if (!objFile.exists()) {
            try {
                objFile.createNewFile();
                return objFile;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void pcmToWave(String inFileName, String outFileName) {
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long longSampleRate = mSampleRateInHZ;
        long totalDataLen = totalAudioLen + 36;
        int channels = 1;//你录制是单声道就是1 双声道就是2（如果错了声音可能会急促等）
        long byteRate = 16 * longSampleRate * channels / 8;

        byte[] data = new byte[mRecordBufferSize];
        try {
            in = new FileInputStream(inFileName);
            out = new FileOutputStream(outFileName);

            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;
            writeWaveFileHeader(out, totalAudioLen, totalDataLen, longSampleRate, channels, byteRate);
            while (in.read(data) != -1) {
                out.write(data);
            }

            in.close();
            out.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    /*
    任何一种文件在头部添加相应的头文件才能够确定的表示这种文件的格式，wave是RIFF文件结构，每一部分为一个chunk，其中有RIFF WAVE chunk，
    FMT Chunk，Fact chunk,Data chunk,其中Fact chunk是可以选择的，
     */
    private static void writeWaveFileHeader(FileOutputStream out, long totalAudioLen, long totalDataLen, long longSampleRate,
                                            int channels, long byteRate) throws IOException {
        byte[] header = new byte[44];
        header[0] = 'R'; // RIFF
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);//数据大小
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';//WAVE
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        //FMT Chunk
        header[12] = 'f'; // 'fmt '
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';//过渡字节
        //数据大小
        header[16] = 16; // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        //编码方式 10H为PCM编码格式
        header[20] = 1; // format = 1
        header[21] = 0;
        //通道数
        header[22] = (byte) channels;
        header[23] = 0;
        //采样率，每个通道的播放速度
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        //音频数据传送速率,采样率*通道数*采样深度/8
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        // 确定系统一次要处理多少个这样字节的数据，确定缓冲区，通道数*采样位数
        header[32] = (byte) (1 * 16 / 8);
        header[33] = 0;
        //每个样本的数据位数
        header[34] = 16;
        header[35] = 0;
        //Data chunk
        header[36] = 'd';//data
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);
        out.write(header, 0, 44);
    }
}
