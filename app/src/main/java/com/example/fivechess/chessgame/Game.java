package com.example.fivechess.chessgame;

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

    private int whichTurn;

    int lineCount = Chess.CHESS_BOARD_LINE_COUNT;

    public Game() {
        chessBoardStatus = new int[lineCount][lineCount];
        whichTurn = Chess.BLACK_CHESS;
    }

    public int[][] getChessBoardStatus(){
        return chessBoardStatus;
    }

    public void addWhiteChess(int x,int y){
        if (chessBoardStatus[x][y]==0){
            chessBoardStatus[x][y] = Chess.WHITE_CHESS;
        }
    }

    public void addBlackChess(int x,int y){
        if (chessBoardStatus[x][y]==0){
            chessBoardStatus[x][y] = Chess.BLACK_CHESS;
        }
    }

    public void addChess(int x,int y){
        if (chessBoardStatus[x][y] == 0){
            chessBoardStatus[x][y] = whichTurn;
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

}
