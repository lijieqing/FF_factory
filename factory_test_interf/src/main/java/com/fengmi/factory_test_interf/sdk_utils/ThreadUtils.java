package com.fengmi.factory_test_interf.sdk_utils;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public final class ThreadUtils {
    private static ExecutorService executor = Executors.newFixedThreadPool(6);
    public static void runCmdOnBack(final String cmd, final int millions) {
        new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(millions);
                    ShellUtil.execCommand(cmd);
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    public static Future<Boolean> runBoolTask(Callable<Boolean> callable) {
        return executor.submit(callable);
    }

    public static Future<String> runStringTask(Callable<String> callable) {
        return executor.submit(callable);
    }
    public static Future<Integer> runIntTask(Callable<Integer> callable) {
        return executor.submit(callable);
    }

}
