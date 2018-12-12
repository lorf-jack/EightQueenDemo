package com.queen.test.testeightqueen;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 八皇后问题的UI演示
 */
public class MainActivity extends Activity {

    private int size = 8;
    //每次设置值的临时位置
    private int[][] currentLocation = new int[size][size];
    /**
     * 存放所有的图片方格
     */
    private Map<String, ImageView> imageMap = new HashMap<>();


    private LinearLayout main_activity_ll;
    private TextView countTv;

    private ColorDrawable redDrawable;
    private ColorDrawable whiteDrawable;
    private ColorDrawable grayDrawable;

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(final Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0://每次尝试
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = msg.getData();
                            String tag = data.getString("tag");
                            for (int j = 0; j < size; j++) {
                                imageMap.get(tag.substring(0, 1) + String.valueOf(j)).setBackgroundColor(Color.WHITE);
                            }
                            ImageView childA = imageMap.get(tag);
                            if (childA != null) {
                                childA.setBackgroundColor(Color.GRAY);
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            WaitThread.notifyResult(QueenUtil.object);
                        }
                    });
                    break;

                case 1://每行成功的尝试

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = msg.getData();
                            String tag = data.getString("tag");
                            ImageView childA = imageMap.get(tag);
                            if (childA != null) {
                                childA.setImageDrawable(redDrawable);
                            }
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            WaitThread.notifyResult(QueenUtil.object);
                        }
                    });
                    break;
                case 2:

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Bundle data = msg.getData();
                            String tag = data.getString("tag");
                            ImageView childA = imageMap.get(tag);
                            if (childA != null) {
                                childA.setImageDrawable(whiteDrawable);
                            }
                            WaitThread.notifyResult(QueenUtil.object);
                        }
                    });

                    break;

                case 3://每次临时设置位置
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            for (int i = 0; i < size; i++) {
                                for (int j = 0; j < size; j++) {
                                    ImageView imageView = imageMap.get(String.valueOf(i) + String.valueOf(j));
                                    if (currentLocation[i][j] == 1) {
                                        imageView.setImageDrawable(grayDrawable);
                                    } else {
                                        imageView.setImageDrawable(whiteDrawable);
                                    }
                                }
                            }
//                            try {
//                                Thread.sleep(500);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            WaitThread.notifyResult(QueenUtil.object);
                        }
                    });
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (size == 0) {
            return;
        }
        initView();

        QueenUtil.QueenLocationCallback callback = new QueenUtil.QueenLocationCallback() {
            @Override
            public void onSetOneLocation(int count, int x, int y) {
//                String tag = String.valueOf(x) + String.valueOf(y);
//                Message message = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("tag", tag);
//                message.setData(bundle);
//                message.what = 0;
//                handler.sendMessage(message);

            }

            @Override
            public void onSetOneLocationCorrect(int count, int x, int y) {
//                String tag = String.valueOf(x) + String.valueOf(y);
//                Message message = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("tag", tag);
//                message.setData(bundle);
//                message.what = 1;
//                handler.sendMessage(message);
            }

            @Override
            public void onSetOneGroupLocationCorrect(int count, int[][] value) {
                resetView(count, value);
            }

            @Override
            public void onReset(int count, int x, int y) {
//                String tag = String.valueOf(x) + String.valueOf(y);
//                Message message = new Message();
//                Bundle bundle = new Bundle();
//                bundle.putString("tag", tag);
//                message.setData(bundle);
//                message.what = 2;
//                handler.sendMessage(message);
            }

            @Override
            public void onSetOneLocation(final int[][] value) {
                currentLocation = value;
                Message message = new Message();
                message.what = 3;
                handler.sendMessage(message);

            }

            @Override
            public void onGetAllDatas(ArrayList<int[][]> allResults) {
                int num = 0;
                for (int[][] item : allResults) {
                    num++;
                    LogUtil.print("第[" + num + "]组数据：");
                    for (int i = 0; i < size; i++) {
                        LogUtil.print(Arrays.toString(item[i]));
                    }
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        QueenUtil.getInstance().getQueen(size, callback);


    }

    private void initView() {
        redDrawable = new ColorDrawable(Color.RED);
        grayDrawable = new ColorDrawable(Color.GRAY);
        whiteDrawable = new ColorDrawable(Color.WHITE);

        main_activity_ll = findViewById(R.id.main_activity_ll);

        int windowW = getResources().getDisplayMetrics().widthPixels;

        for (int i = 0; i < size; i++) {

            final LinearLayout linearLayout_row = new LinearLayout(MainActivity.this);
            linearLayout_row.setLayoutParams(new LinearLayout.LayoutParams(windowW, (int) (windowW / size)));
            for (int j = 0; j < size; j++) {
                ImageView imageView = new ImageView(MainActivity.this);
                imageView.setTag(String.valueOf(i) + String.valueOf(j));
                imageView.setBackgroundColor(Color.BLACK);
                imageView.setPadding(2, 2, 2, 2);
                imageView.setImageDrawable(whiteDrawable);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams((int) (windowW / size), (int) (windowW / size));
                imageView.setLayoutParams(layoutParams);
                imageMap.put(String.valueOf(i) + String.valueOf(j), imageView);
                linearLayout_row.addView(imageView);
            }
            main_activity_ll.addView(linearLayout_row);
        }

        countTv = new TextView(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        countTv.setLayoutParams(layoutParams);
        countTv.setText("第" + "0" + "组");
        main_activity_ll.addView(countTv);
    }

    /**
     * 成功一组
     *
     * @param count
     * @param datas
     */
    private void resetView(final int count, final int[][] datas) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                countTv.setText("第" + String.valueOf(count) + "组");
                for (int i = 0; i < size; i++) {
                    for (int j = 0; j < size; j++) {
                        ImageView imageView = imageMap.get(String.valueOf(i) + String.valueOf(j));
                        if (datas[i][j] == 1) {
                            imageView.setImageDrawable(redDrawable);
                        } else {
                            imageView.setImageDrawable(whiteDrawable);
                        }
                    }
                }
//                try {
//                    Thread.sleep(500);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                WaitThread.notifyResult(QueenUtil.object);
            }
        });
    }

}
