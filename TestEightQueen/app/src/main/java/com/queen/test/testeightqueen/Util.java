package com.queen.test.testeightqueen;

import java.util.Arrays;

/**
 * Created by jack_lorf on 18/12/11.
 */

public class Util {

    private static int SIZE = 8;

    private static int[] result = new int[SIZE];


    public static void getResult() {

    }

    static int count = 0;

    public static void get(int row) {

        for (int i = 0; i <SIZE; i++) {
            result[row] = i;
            if (isCanSet(row)) {
                if (row < SIZE-1) {
                    get(row + 1);
                } else {
                    count++;
                    LogUtil.print("结果[" + count + "]=" + Arrays.toString(result));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    public static boolean isCanSet(int m) {
        for (int i = 0; i < m; i++) {
            if (result[i] == result[m] || Math.abs(result[i] - result[m]) == Math.abs(i - m)) {
                return false;
            }
        }
        return true;
    }
}
