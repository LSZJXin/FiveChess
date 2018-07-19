package com.example.fivechess.chessgame;


import java.util.Deque;
import java.util.LinkedList;

/**
 * 整个五子棋游戏的逻辑类
 *
 * Created by 张佳欣 on 2018/7/11.
 */
public class Game {

    /**
     * 棋盘的状态
     */
    int [][] chessBoardStatus;

    /**
     * 用以表示轮到谁下棋
     */
    private int whichTurn;

    /**
     * 棋盘线数
     */
    int lineCount = Chess.CHESS_BOARD_LINE_COUNT;

    /**
     * 用一个双向队列来表示下棋的历史，用以完成悔棋功能
     */
    private static Deque<Coordinate> historyList;

    /**
     * 表示最后一颗棋子位置
     */
    Coordinate latestChess = null;

    //默认模式为双人对弈
    private int gameMode = GameMode.TWO_FIGHT;

    public Game() {
        //初始化棋盘的状态
        chessBoardStatus = new int[lineCount][lineCount];
        for (int i=0;i<chessBoardStatus.length;i++){
            for (int j=0;j<chessBoardStatus[i].length;j++){
                chessBoardStatus[i][j] = Chess.BLANK_CHESS;
            }
        }
        //黑子先下
        whichTurn = Chess.BLACK_CHESS;
        historyList = new LinkedList<>();
    }

    /**
     * 设置游戏模式
     *
     * @param gameMode
     */
    public void setGameMode(int gameMode){
        this.gameMode = gameMode;
    }

    /**
     * 获取棋盘信息
     *
     * @return
     */
    public int[][] getChessBoardStatus(){
        return chessBoardStatus;
    }

    /**
     * 获取最新下的棋子
     *
     * @return
     */
    public Coordinate getLatestChess(){
        return latestChess;
    }

    /**
     * 返回历史列表
     *
     * @return
     */
    public static Deque<Coordinate> getHistoryList(){
        return historyList;
    }

    /**
     * 添加白棋
     *
     * @param x
     * @param y
     */
    public void addWhiteChess(int x,int y){
        if (chessBoardStatus[x][y]==Chess.BLANK_CHESS){
            chessBoardStatus[x][y] = Chess.WHITE_CHESS;
            historyList.add(new Coordinate(x,y));
            latestChess = new Coordinate(x,y);
            changeTurn();
        }
    }

    /**
     * 添加黑棋
     *
     * @param x
     * @param y
     */
    public void addBlackChess(int x,int y){
        if (chessBoardStatus[x][y]==Chess.BLANK_CHESS){
            chessBoardStatus[x][y] = Chess.BLACK_CHESS;
            historyList.add(new Coordinate(x,y));
            latestChess = new Coordinate(x,y);
            changeTurn();
        }
    }

    /**
     * 添加棋子
     *
     * @param x
     * @param y
     */
    public void addChess(int x,int y){
        if (chessBoardStatus[x][y] == Chess.BLANK_CHESS){
            chessBoardStatus[x][y] = whichTurn;
            historyList.add(new Coordinate(x,y));
            latestChess = new Coordinate(x,y);
            //下完棋后就改变下棋一方
            changeTurn();
            if (gameMode == GameMode.PERSON_COMPUTER){
                addChessWithAIMode();
            }
        }
    }

    public void addChessWithAIMode(){
        //轮到电脑下棋的话
        if (whichTurn == Chess.WHITE_CHESS){
            Coordinate c = GameAI.getBestCoordinate(chessBoardStatus);
            addChess(c.x,c.y);
        }
    }

    /**
     * 换人下棋
     */
    public void changeTurn(){
        if (whichTurn==Chess.BLACK_CHESS){
            whichTurn = Chess.WHITE_CHESS;
        }else if (whichTurn == Chess.WHITE_CHESS){
            whichTurn = Chess.BLACK_CHESS;
        }
    }

    /**
     * 悔棋
     */
    public void rollback(){
        Coordinate c = historyList.pollLast();
        if (c != null && !c.equals(null)){

            chessBoardStatus[c.getX()][c.getY()] = Chess.BLANK_CHESS;

            //如果还有棋子,则将最新棋子回退
            if (historyList.size()>0) {
                //重新设置最新的棋子
                Coordinate latest = historyList.getLast();
                if (latest != null || !c.equals(null)) {
                    latestChess = new Coordinate(latest);
                }
            }

            //更换下棋一方
            changeTurn();
        }

    }

    /**
     * 清空棋盘
     */
    public void resetGame(){
        chessBoardStatus = new int[lineCount][lineCount];
        for(int i=0;i<chessBoardStatus.length;i++){
            for(int j=0;j<chessBoardStatus[i].length;j++){
                chessBoardStatus[i][j] = Chess.BLANK_CHESS;
            }
        }
    }
}
