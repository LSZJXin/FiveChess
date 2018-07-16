package com.example.fivechess.chessgame;

/**
 * Created by 张佳欣 on 2018/7/16.
 *
 * 游戏规则类
 */
public class GameRule {

    //和棋
    public static final int NO_WIN_NO_LOSE = 0;

    //白方胜
    public static final int WHITE_WIN = 1;

    //黑方胜
    public static final int BLACK_WIN = 2;

    //未定
    public static final int NO_RESULT = 3;

    /**
     * 加入棋子后判断结果
     *
     * @param chessBoardState 棋盘格局
     * @param join 新加入的棋子
     * @return
     */
    public static int judgeResult(int [][]chessBoardState,Coordinate join){
        return judgeResult(chessBoardState,join.getX(),join.getY());
    }

    /**
     * 加入棋子后，判断结果
     *
     * @param chessBoardState
     * @param x
     * @param y
     * @return
     */
    public static int judgeResult(int [][]chessBoardState,int x,int y){

        int join = chessBoardState[x][y];

        if (Game.getHistoryList().size()==15*15){
            return NO_WIN_NO_LOSE;
        }

        if(join == Chess.BLACK_CHESS){
            return judge(chessBoardState,x,y,Chess.BLACK_CHESS);
        }else if(join == Chess.WHITE_CHESS){
            return judge(chessBoardState,x,y,Chess.WHITE_CHESS);
        }

        return NO_RESULT;
    }

    /**
     * @param chessBoardState 棋盘状态
     * @param x 新加入的x
     * @param y 新加入的y
     * @param type 加入的类型
     * @return 返回一个整形，如果赢则返回1，如果没赢，则返回0
     */
    private static int judge(int [][]chessBoardState,int x,int y,int type){

        int left = x-4<0 ? 0 : x-4;
        int right = x+4>14 ? 14 : x+4;
        int top = y-4<0 ? 0 : y-4;
        int bottom = y+4>14 ? 14 : y+4;

        int count = 0;

        boolean isWin = false;

        //横向
        for (int i=left;i<=right;i++){
            if (chessBoardState[i][y] == type){
                count++;
            }
        }
        if (count>=5){
            isWin = true;
        }

        count = 0;

        //纵向
        for (int i=top;i<=bottom;i++){
            if (chessBoardState[x][i]==type){
                count++;
            }
        }
        if (count>=5){
            isWin = true;
        }

        count = 0;

        //斜向右下方向
        for (int i=left,j=top;i<=right && j<=bottom;i++,j++){
            if (chessBoardState[i][j] == type){
                count++;
            }
        }
        if (count>=5){
            isWin = true;
        }

        count = 0;

        //斜右向上
        for(int i=left,j=bottom;i<=right && j>=top;i++,j--){
            if (chessBoardState[i][j] == type){
                count++;
            }
        }

        if (count>=5){
            isWin = true;
        }

        if (isWin){
            if (type == Chess.WHITE_CHESS){
                return WHITE_WIN;
            }else {
                return BLACK_WIN;
            }
        }
        return NO_RESULT;
    }
}
