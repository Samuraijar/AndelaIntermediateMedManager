package com.example.android.andelaintermediatemedmanager.Utils;

/**
 * Created by NORMAL on 4/15/2018.
 */

public class AppTager {
    public static String getTag() {
        String tag = "";
        final StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
        for (int i = 0; i <stackTraceElements.length; i++) {
            if (stackTraceElements[i].getMethodName().equals("getTag")) {
                tag = "("+stackTraceElements[i + 1].getFileName() + ":" + stackTraceElements[i + 1].getLineNumber()+")";

            }
        }
        return tag;
    }
}
