package com.example.toan.util;

import android.util.Log;

/**
 * Created by Toan on 11-Feb-18.
 */

public class LogUtil {
    static boolean isLogEnable = true;

    public static void d(String tag, String msg) {  //debug
        if(isLogEnable)
            Log.d(tag, msg);
    }

    public static void e(String tag, String msg) {  //error
        if(isLogEnable)
            Log.e(tag, msg);
    }

    public static void v(String tag, String msg) {  //verbose
        if(isLogEnable)
            Log.v(tag, msg);
    }
}
