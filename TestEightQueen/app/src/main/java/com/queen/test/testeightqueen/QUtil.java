package com.queen.test.testeightqueen;

/**
 * Created by jack_lorf on 18/12/11.
 */

public class QUtil {


    private int QUEEN_COUNT = 0; // 皇后的默认数量

    private int[][] Queencount;// 分配8X8的数组，充当棋盘，存放皇后

    private int resultCount = 0;// 记录皇后的放置方法的总数

    private int[] Queenplace;// 对于索引n, Queenplace[n]表示第n行的皇后放置位置是第Queenplace[n]列

    public QUtil(int n) {
        this.QUEEN_COUNT = n;
        this.resultCount = 0;
        this.Queencount = new int[QUEEN_COUNT][QUEEN_COUNT];
        Queenplace = new int[QUEEN_COUNT];
    }

    public void putQueen() {
        putQueen(0);
    }

    private void putQueen(int row0) {
        int row = row0;// 行标
        for (int column = 0; column < QUEEN_COUNT; column++)// column表示列标，该层循环的作用是用于询问第row行，第column列是否可以放置皇后
        {
            if (Queencount[row][column] == 0)// 如果第row行、第column列可以放皇后
            {
                for (int nextRow = row + 1; nextRow < QUEEN_COUNT; nextRow++)// 该层for循环的作用是使其斜下方和正下方不为0
                {
                    //将不同行的同一列（正下方）标记为非零，表示不能再在该列放置皇后了
                    Queencount[nextRow][column]++;

                    //通过该层循环将第row行、第column列的正对角线上的位置标记为非零，表示不能在这些位置放置皇后
                    if (column - nextRow + row >= 0) {
                        Queencount[nextRow][column - nextRow + row]++;
                    }

                    //通过该层循环将第row行、第column列的反对角线上的位置标记为非零，表示不能在这些位置放置皇后
                    if (column + nextRow - row < QUEEN_COUNT) {
                        Queencount[nextRow][column + nextRow - row]++;
                    }
                }
                // 记录下第row行第column列放置了皇后
                Queenplace[row] = column;

                // 如果各行都放置了皇后，也就是说如果皇后已放满，打印出皇后布局
                if (row == QUEEN_COUNT - 1) {
                    printQueen(++resultCount);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else // 否则递归继续排列下一行皇后
                {
                    putQueen(row + 1);
                }
                for (int rows = row + 1; rows < QUEEN_COUNT; rows++)// 回溯，使得在第row行的皇后不放在第column列,那放置在那一列？
                // 答案是通过该算法的最外层循环，利用最外层for循环将皇后放在这一行的其他列
                {
                    //既然第row行、第column列不放置皇后了，则需要恢复正下方的不可用标记，将不同行的同一列的非零标记还原，也即恢复该位置的正下面的标记
                    Queencount[rows][column]--;

                    //还原第row行、第column列的正对角线上的位置标记
                    if (column - rows + row >= 0) {
                        Queencount[rows][column - rows + row]--;
                    }
                    //还原第row行、第column列的反对角线上的位置标记
                    if (column + rows - row < QUEEN_COUNT) {
                        Queencount[rows][column + rows - row]--;
                    }
                }
            }
        }
        if (row == 0) {
            System.out.println(QUEEN_COUNT + "皇后问题共有" + resultCount + "个解.");
        }
    }

    private void printQueen(final int size)// 打印皇后布局
    {
        System.out.println(QUEEN_COUNT + "皇后的第" + size + "个解是:");
        System.out.println();
        for (int row = 0; row < QUEEN_COUNT; row++) {
            for (int column = 0; column < QUEEN_COUNT; column++) {
                System.out.print(Queenplace[row] == column ? " * " : " - ");
            }
            System.out.println();
        }
        System.out.println();
    }

}
