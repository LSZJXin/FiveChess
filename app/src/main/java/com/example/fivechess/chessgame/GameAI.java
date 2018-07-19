package com.example.fivechess.chessgame;


import android.util.Log;

/**
 * Created by 张佳欣 on 2018/7/12.
 *
 * 五子棋游戏的AI，包括判断输赢
 */
public class GameAI {

    private static final int lineCount = Chess.CHESS_BOARD_LINE_COUNT;

    /**
     * 用四维数组存储空格8个方向的状态信息
     * 即8个方向每个方向连续的棋子状态
     * 前两个[]表示棋盘的横竖位置
     * 第三个[]表示向8个方向扩展
     * 第四个[]表示从黑或者白棋的角度计算，0为黑，1为白
     */
    private static int[][][][] chessLineStates;

    /**
     * 黑棋在该位置的估值
     */
    private static int[][] black;

    /**
     * 白棋在该位置的估值
     */
    private static int[][] white;


    /**
     * 计算下棋的最佳位置
     *
     * @param chessBoardStates
     * @return
     */
    public static Coordinate getBestCoordinate(int[][] chessBoardStates){
        calculateValue(chessBoardStates);
        int maxBlack = 0,maxWhite = 0;

        int blackX = 0,blackY = 0;
        int whiteX = 0,whiteY = 0;

        for (int i=0;i<lineCount;i++){
            for (int j=0;j<lineCount;j++){
                if (maxBlack<black[i][j]){
                    maxBlack = black[i][j];
                    blackX = i;
                    blackY = j;
                }

                if (maxWhite<white[i][j]){
                    maxWhite = white[i][j];
                    whiteX = i;
                    whiteY = j;
                }
            }
        }

        if (maxBlack>maxWhite){
            return new Coordinate(blackX,blackY);
        }else {
            return new Coordinate(whiteX,whiteY);
        }
    }

    /**
     * 判断空格位置8个方向连线情况
     *
     *
     */
    public static void calculateLine(int[][] chessBoardStates){

        chessLineStates = new int[lineCount][lineCount][8][2];

        //通过增量来控制遍历方向
        //用两个一维数组，方便遍历8个方向
        //(0,-1)向上 (1，-1)斜右向上
        //(1,0)向右 (1,1)斜右向下
        //(0,1)向下 (-1,1)斜左向下
        //(-1,0)向左 (-1,-1)斜左向
        int []dx = {0, 1, 1, 1, 0, -1, -1, -1};
        int []dy = {-1, -1, 0, 1, 1, 1, 0, -1};

        //通过增量计算后，目前的位置
        int tx,ty;

        int count;

        //遍历整个棋盘
        for (int i=0;i<lineCount;i++){
            for (int j=0;j<lineCount;j++){

                //只计算空格的位置的值
                if (chessBoardStates[i][j]==Chess.BLANK_CHESS){

                    //遍历8个方向
                    for (int k=0;k<8;k++){

                        //从黑棋角度出发，计算
                        count = 0;
                        tx = i;
                        ty = j;
                        //最多只需向前遍历4个棋子
                        for (int t=0;t<5;t++){
                            tx += dx[k];
                            ty += dy[k];
                            //越界直接退出
                            if (tx<0||ty<0||tx>14||ty>14){
                                break;
                            }

                            if (chessBoardStates[tx][ty]==Chess.BLANK_CHESS){
                                //遇到空格
                                //值加3，退出这个方向的遍历
                                count += 3;
                                break;
                            }else if(chessBoardStates[tx][ty]==Chess.BLACK_CHESS){
                                //遇到黑棋
                                //值加5，继续在这个方向遍历
                                count += 5;
                            }else if (chessBoardStates[tx][ty]==Chess.WHITE_CHESS){
                                //遇到白棋
                                //值加3，继续在这个方向遍历
                                count += 2;
                                break;
                            }
                        }
                        //BLACK
                        chessLineStates[i][j][k][0] = count;

                        //从白棋角度出发，计算
                        tx = i;
                        ty = j;
                        count = 0;
                        for (int t=0;t<5;t++){
                            tx += dx[k];
                            ty += dy[k];
                            //越界直接退出
                            if (tx<0||ty<0||tx>14||ty>14){
                                count = count+2;
                                break;
                            }

                            if (chessBoardStates[tx][ty]==Chess.BLANK_CHESS){
                                //遇到空格
                                //值加3，退出这个方向的遍历
                                count += 3;
                                break;
                            }else if(chessBoardStates[tx][ty]==Chess.WHITE_CHESS){
                                //遇到白棋
                                //值加5，继续在这个方向遍历
                                count += 5;
                            }else if (chessBoardStates[tx][ty]==Chess.BLACK_CHESS){
                                //遇到黑棋
                                //值加3，继续在这个方向遍历
                                count += 2;
                                break;
                            }
                        }
                        //WHITE
                        chessLineStates[i][j][k][1] = count;
                    }
                }

            }
        }
    }

    public static void calculateValue(int[][] chessBoardStates){

        calculateLine(chessBoardStates);

        int value;
        black = new int[lineCount][lineCount];
        white = new int[lineCount][lineCount];

        for (int i=0;i<lineCount;i++){
            for (int j=0;j<lineCount;j++) {

                if (chessBoardStates[i][j] == Chess.BLANK_CHESS) {
                    value = 0;
                    for (int k = 0; k < 4; k++) {

                        int temp = chessLineStates[i][j][k][0] + chessLineStates[i][j][k + 4][0];
                        switch (temp) {
                            //长连
                            case 46:
                            case 45:
                            case 44:
                            case 41:
                            case 40:
                            case 39:
                            case 36:
                            case 35:
                            case 34:
                            case 31:
                            case 30:
                            case 29:
                            case 26:
                            case 25:
                            case 24:
                                value += 100000;
                                break;
                            //活四
                            case 21:
                                value += 40000;
                                break;
                            //眠四
                            case 20:
                                value += 500;
                                break;
                            //死四
                            case 19:
                                value -= 5;
                                break;
                            //活三
                            case 16:
                                value += 200;
                                break;
                            //眠三
                            case 15:
                                value += 50;
                                break;
                            //死三
                            case 14:
                                value -= 10;
                                break;
                            //活二
                            case 11:
                                value += 10;
                                break;
                            //眠二
                            case 10:
                                value += 7;
                                break;
                            //死二
                            case 9:
                                value -= 5;
                                break;
                            case 6:
                                value += 5;
                                break;
                            case 5:
                                value += 3;
                                break;
                            case 4:
                                value -= 5;
                                break;
                            default:
                                break;
                        }
                    }
                    black[i][j] = value;

                    value = 0;
                    for (int k = 0; k < 4; k++) {

                        int temp = chessLineStates[i][j][k][1] + chessLineStates[i][j][k + 4][1];

                        switch (temp) {
                            //长连
                            case 46:
                            case 45:
                            case 44:
                            case 41:
                            case 40:
                            case 39:
                            case 36:
                            case 35:
                            case 34:
                            case 31:
                            case 30:
                            case 29:
                            case 26:
                            case 25:
                            case 24:
                                value += 100000;
                                break;
                            //活四
                            case 21:
                                value += 40000;
                                break;
                            //眠四
                            case 20:
                                value += 500;
                                break;
                            //死四
                            case 19:
                                value -= 5;
                                break;
                            //活三
                            case 16:
                                value += 200;
                                break;
                            //眠三
                            case 15:
                                value += 50;
                                break;
                            //死三
                            case 14:
                                value -= 10;
                                break;
                            //活二
                            case 11:
                                value += 10;
                                break;
                            //眠二
                            case 10:
                                value += 7;
                                break;
                            //死二
                            case 9:
                                value -= 5;
                                break;
                            case 6:
                                value += 5;
                                break;
                            case 5:
                                value += 3;
                                break;
                            case 4:
                                value -= 5;
                                break;
                            default:
                                break;
                        }
                    }
                    white[i][j] = value;
                }
            }
        }

    }

}
