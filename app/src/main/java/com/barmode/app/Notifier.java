package com.barmode.app;

import android.util.Log;


public class Notifier {

    public static void log(String label){
        String logMessage = String.format("%s, Process ID:%d, Thread ID:%d", label, android.os.Process.myPid(), android.os.Process.myTid());
        Log.i("com.barmode", logMessage);
    }
}
