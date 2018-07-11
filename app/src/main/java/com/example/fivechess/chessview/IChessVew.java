package com.example.fivechess.chessview;

import android.graphics.Canvas;

/**
 * 棋盘绘制的统一接口
 *
 * Created by 张佳欣 on 2018/7/10.
 */
public interface IChessVew {

    //绘制棋盘
    void drawChessBoard();

    //绘制棋子
    void drawChess(Canvas canvas);

    //绘制棋盘的5个焦点
    void drawBoardFocus(Canvas canvas);

    //绘制整个游戏的所有元素
    void drawGame();
}
