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
    Deque<Coordinate> historyList;

    /**
     * 表示最后一颗棋子位置
     */
    Coordinate latestChess = null;

    public Game() {
        chessBoardStatus = new int[lineCount][lineCount];
        whichTurn = Chess.BLACK_CHESS;
        historyList = new LinkedList<>();
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
     * 添加白棋
     *
     * @param x
     * @param y
     */
    public void addWhiteChess(int x,int y){
        if (chessBoardStatus[x][y]==0){
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
        if (chessBoardStatus[x][y]==0){
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
        if (chessBoardStatus[x][y] == 0){
            chessBoardStatus[x][y] = whichTurn;
            historyList.add(new Coordinate(x,y));
            latestChess = new Coordinate(x,y);
            changeTurn();
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
        Coordinate c = historyList.getLast();
        if (c != null && !c.equals(null)){
            historyList.remove();
            chessBoardStatus[c.getX()][c.getY()] = 0;

            //重新设置最新的棋子
            Coordinate latest = historyList.getLast();
            if (latest!=null || !c.equals(null)){
                latestChess = new Coordinate(latest);
            }

            //更换下棋一方
            changeTurn();
        }

    }

}
