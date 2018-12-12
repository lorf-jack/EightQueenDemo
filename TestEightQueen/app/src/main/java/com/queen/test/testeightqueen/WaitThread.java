package com.queen.test.testeightqueen;


/**
 * Created by zhangyinyuan on 2017/1/9.
 */
public class WaitThread {

    public static void waitResult(Object obj) {
        synchronized (obj) {
            try {
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                LogUtil.print("waitResult 出错");
            }
        }
    }

    public static void notifyResult(Object obj) {
        synchronized (obj) {
            obj.notify();
        }
    }
}