package com.queen.test.testeightqueen;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

/**
 * 工具类:日志
 * Created by jack_lorf on 18/12/10.
 */

public class LogUtil {

    private static boolean isPrintLog = true;

    private final static LevelEnum DEFAULT_LEVEL = LevelEnum.LEVEL_1;

    private final static String DEFAULT_TAG = "ZYA";


    public static void print(final String msg) {
        print(null, null, msg);
    }

    public static void print(LevelEnum level, String msg) {
        print(level, null, msg);
    }

    public static void print(String tag, String msg) {
        print(null, tag, msg);
    }

    public static void print(LevelEnum level, String tag, String msg) {
        if (!isPrintLog) {
            return;
        }
        if (level == null) {
            level = DEFAULT_LEVEL;
        }
        if (TextUtils.isEmpty(tag)) {
            tag = DEFAULT_TAG;
        }
        if (TextUtils.isEmpty(msg)) {
            Log.v(tag, "");
        } else {
            int length = msg.length();
            String currentMsg = "";
            String leaveMsg = "";
            if (length > 2000) {
                currentMsg = msg.substring(0, 2000);
                leaveMsg = msg.substring(2000);
            } else {
                currentMsg = msg;
                leaveMsg = "";
            }
            if (level == LevelEnum.LEVEL_1) {
                Log.v(tag, currentMsg);
            } else if (level == LevelEnum.LEVEL_2) {
                Log.d(tag, currentMsg);
            } else if (level == LevelEnum.LEVEL_3) {
                Log.i(tag, currentMsg);
            } else if (level == LevelEnum.LEVEL_4) {
                Log.w(tag, currentMsg);
            } else if (level == LevelEnum.LEVEL_5) {
                Log.e(tag, currentMsg);
            }
            if (!TextUtils.isEmpty(leaveMsg)) {
                print(level, tag, leaveMsg);
            }
        }
    }


    public enum LevelEnum {
        LEVEL_1(1, "verbose"),//开发
        LEVEL_2(2, "debug"),//调试
        LEVEL_3(3, "info"),//普通
        LEVEL_4(4, "warn"),//警告
        LEVEL_5(5, "error");//错误

        private int levelCode;
        private String levelDesc;

        LevelEnum(int code, String desc) {
            this.levelCode = code;
            this.levelDesc = desc;
        }

        public int getLevelCode() {
            return levelCode;
        }

        public void setLevelCode(int levelCode) {
            this.levelCode = levelCode;
        }

        public String getLevelDesc() {
            return levelDesc;
        }

        public void setLevelDesc(String levelDesc) {
            this.levelDesc = levelDesc;
        }
    }


}
