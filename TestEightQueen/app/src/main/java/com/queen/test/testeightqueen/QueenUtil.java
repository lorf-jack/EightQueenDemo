package com.queen.test.testeightqueen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jack_lorf on 18/12/10.
 */

public class QueenUtil {


    /**
     * 单次结果
     */
    private int result[][];
    /**
     * 全部结果
     */
    private ArrayList<int[][]> allResults = new ArrayList<>();
    /**
     * 可行结果个数
     */
    private int count = 0;
    /**
     * 执行每个查询的时间间隔
     */
    private int TIME_INTERVAL = 200;

    private static QueenUtil instance = null;

    private QueenLocationCallback callback;

    public static Object object = new Object();

    private Timer timer;

    public static QueenUtil getInstance() {
        if (instance == null) {
            instance = new QueenUtil();
        }
        return instance;
    }

    public void setCallback(QueenLocationCallback callback) {
        this.callback = callback;
    }

    private void init(int size, QueenLocationCallback callback) {
        result = new int[size][size];
        timer = new Timer();
        this.setCallback(callback);
    }

    public void getQueen(final int size, final QueenLocationCallback callback) {
        init(size, callback);
        if (timer != null) {
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    setLocation(size, 0);
                }
            }, 0);
        }
    }

    private void setLocation(final int size, final int row) {
        result[row] = new int[size];
        for (int i = 0; i < size; i++) {
            result[row] = new int[size];
            result[row][i] = 1;
            if (row == 1 && i == size - 1 && (result[row - 1][i] == 1 && result[row][i] == 1)) {
                LogUtil.print("第一和第二行的末尾位都是1");
                stopTimer();
                callback.onGetAllDatas(allResults);
                return;
            }
            if (callback != null) {
//                callback.onSetOneLocation(count, row, i);
                callback.onSetOneLocation(result);
            }
            WaitThread.waitResult(object);
            if (isCanLocation(size, row, i)) {
//                if (callback != null) {
//                    callback.onSetOneLocationCorrect(count, row, i);
//                }
//                WaitThread.waitResult(object);
                if (row < size - 1) {
                    setLocation(size, row + 1);
                } else {
                    count++;
                    synchronized ("") {
                        allResults.add(result);
                        if (callback != null) {
                            callback.onSetOneGroupLocationCorrect(count, result);
                        }
                        WaitThread.waitResult(object);
                        LogUtil.print("第[" + count + "]个数据");
                        for (int m = 0; m < size; m++) {
                            LogUtil.print(Arrays.toString(result[m]));
                        }
                    }
                }
            } else {
                if (i == size - 1) {
//                    result[row][i]=0;//可以处理回返单行，但是多行回溯不行

                    if (row > 0) {
                        for (int j = row; j > 0; j--) {//处理单／多行回溯
                            if (result[j][size - 1] == 1) {
                                result[j][size - 1] = 0;
                            } else {
                                break;
                            }
                        }
                    }
                }


//                if (callback != null) {
//                    callback.onReset(count, row, i);
//                }
//                WaitThread.waitResult(object);
            }
        }


    }

    private boolean isCanLocation(int size, int row, int column) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < size; j++) {
                if (result[i][j] == 1) {
                    if (row == i || column == j || Math.abs(row - i) == Math.abs(column - j)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }


    interface QueenLocationCallback {

        void onSetOneLocation(int count, int x, int y);//每次设置位置都调用，但不保证是否合理

        void onSetOneLocationCorrect(int count, int x, int y);//设置当前行合理的位置调用

        void onSetOneGroupLocationCorrect(int count, int[][] value);//设置一组合理的位置

        void onReset(int count, int x, int y);//重置当前的位置，但是无法重置上一行的位置

        void onSetOneLocation(int[][] value);//此种方式可能会更好，每次都刷新单组所有的view

        void onGetAllDatas(ArrayList<int[][]> allResults);//获取全部数据
    }

    private void stopTimer() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }


}
