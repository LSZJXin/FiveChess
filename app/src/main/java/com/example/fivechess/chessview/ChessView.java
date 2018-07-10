package com.example.fivechess.chessview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.fivechess.R;
import com.example.fivechess.chessgame.Chess;

/**
 * Created by 张佳欣 on 2018/7/9.
 */
public class ChessView extends SurfaceView implements SurfaceHolder.Callback,IChessVew{

    private static final String TAG = "ChessView";

    /**
     * 白棋的画笔
     */
    private Paint whiteChessPaint;

    /**
     * 黑棋的画笔
     */
    private Paint blackChessPaint;

    /**
     * 棋盘线的画笔
     */
    private Paint chessBoardLinePaint;

    /**
     * 棋盘线的宽度
     */
    private int chessBoardLineWidth;

    /**
     * 白棋颜色
     */
    private int whiteColor;

    /**
     * 黑棋颜色
     */
    private int blackColor;

    /**
     * 棋盘线颜色
     */
    private int chessBoardLineColor;

    /**
     * 棋盘底色
     */
    private int boardBackgroundColor;

    /**
     * 棋盘线数
     */
    private int lineCount = Chess.CHESS_BOARD_LINE_COUNT;

    //棋盘的左上角和右下角
    float startX,startY,stopX,stopY;

    /**
     * 棋子半径
     */
    float chessRadius;

    /**
     * 一个棋格的宽度
     */
    float oneWidth;


    private SurfaceHolder mHolder;

    public ChessView(Context context) {
        super(context);
    }

    public ChessView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public ChessView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获取属性
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.chess_board,0,0);
        chessBoardLineWidth = t.getDimensionPixelSize(R.styleable.chess_board_chess_line_width,10);
        whiteColor = t.getColor(R.styleable.chess_board_white_chess_color, Color.WHITE);
        blackColor = t.getColor(R.styleable.chess_board_black_chess_color,Color.BLACK);
        chessBoardLineColor = t.getColor(R.styleable.chess_board_chess_line_color,Color.GRAY);
        boardBackgroundColor = t.getColor(R.styleable.chess_board_board_background_color,Color.GRAY);
        t.recycle();

        //白色棋子的画笔设置
        whiteChessPaint = new Paint();
        whiteChessPaint.setColor(whiteColor);
        whiteChessPaint.setStyle(Paint.Style.FILL);
        whiteChessPaint.setAntiAlias(true);

        //黑色棋子的画笔设置
        blackChessPaint = new Paint();
        blackChessPaint.setColor(blackColor);
        blackChessPaint.setStyle(Paint.Style.FILL);
        blackChessPaint.setAntiAlias(true);

        //棋盘线的画笔设置
        chessBoardLinePaint = new Paint();
        chessBoardLinePaint.setColor(chessBoardLineColor);
        chessBoardLinePaint.setStrokeWidth(chessBoardLineWidth);
        chessBoardLinePaint.setStyle(Paint.Style.FILL);
        chessBoardLinePaint.setAntiAlias(true);

        initView();
    }

    /**
     * 初始化
     */
    private void initView(){
        mHolder = getHolder();
        mHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
        this.setKeepScreenOn(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int chessWidth = getMeasuredWidth();
        int chessHeight = chessWidth;
        setMeasuredDimension(chessWidth,chessHeight);

        oneWidth = (chessWidth - getLeft())*1.0f/lineCount;

        startX = getLeft() + (oneWidth/2);
        startY = getTop() + (oneWidth/2);
        stopX = oneWidth*(lineCount-1) + startX;
        stopY = oneWidth*(lineCount-1) + startY;

        chessRadius = (7*oneWidth)/16;
    }

    /**
     * 画棋盘
     */
    @Override
    public void drawChessBoard(){
        //获取Canvas对象
        Canvas canvas = mHolder.lockCanvas();
        if (mHolder==null || canvas==null){
            return;
        }
        //画棋盘
        drawChessBoard(canvas);
        //画棋盘焦点
        drawBoardFocus(canvas);
        drawWhiteChess(canvas,8,8);
        drawWhiteChess(canvas,12,12);
        drawBlackChess(canvas,11,11);
        drawBlackChess(canvas,6,6);
        drawWhiteChessFocus(canvas,8,8);
        drawBlackChessFocus(canvas,6,6);
        //对画布内容进行提交
        mHolder.unlockCanvasAndPost(canvas);
    }

    @Override
    public void drawChess() {

    }

    /**
     * 画棋盘
     *
     * @param canvas
     */
    private void drawChessBoard(Canvas canvas){

        canvas.drawColor(boardBackgroundColor);

        for (int i=0;i<lineCount;i++){
            float y = startY + (oneWidth*i);
            float x = startX + (oneWidth*i);
            //画横线
            canvas.drawLine(startX,y,stopX,y,chessBoardLinePaint);
            //画竖线
            canvas.drawLine(x,startY,x,stopY,chessBoardLinePaint);
        }
    }

    /**
     * 绘制棋盘焦点
     *
     * @param canvas 画布
     */
    @Override
    public void drawBoardFocus(Canvas canvas) {
        float radius = 6;
        canvas.drawCircle(startX+(oneWidth*3),startY+(oneWidth*3),radius,chessBoardLinePaint);
        canvas.drawCircle(startX+(oneWidth*11),startY+(oneWidth*3),radius,chessBoardLinePaint);
        canvas.drawCircle(startX+(oneWidth*3),startY+(oneWidth*11),radius,chessBoardLinePaint);
        canvas.drawCircle(startX+(oneWidth*11),startY+(oneWidth*11),radius,chessBoardLinePaint);
        canvas.drawCircle(startX+(oneWidth*7),startY+(oneWidth*7),radius,chessBoardLinePaint);
    }

    @Override
    public void drawGame() {

    }

    /**
     * 画白棋
     *
     * @param canvas 画布
     * @param x 棋盘第x列
     * @param y 棋盘第y行
     */
    void drawWhiteChess(Canvas canvas,int x,int y){
        canvas.drawCircle(startX+(oneWidth*x),startY+(oneWidth*y),chessRadius,whiteChessPaint);
    }

    /**
     * 画黑棋
     *
     * @param canvas 画布
     * @param x 棋盘第x列
     * @param y 棋盘第y行
     */
    void drawBlackChess(Canvas canvas,int x,int y){
        canvas.drawCircle(startX+(oneWidth*x),startY+(oneWidth*y),chessRadius,blackChessPaint);
    }

    /**
     * 画白棋的焦点
     *
     * @param canvas 画布
     * @param x 棋盘第x列
     * @param y 棋盘第y行
     */
    void drawWhiteChessFocus(Canvas canvas,int x,int y){
        float radius = 5;
        canvas.drawCircle(startX+(oneWidth*x),startY+(oneWidth*y),radius,blackChessPaint);
    }

    void drawBlackChessFocus(Canvas canvas,int x,int y){
        float radius = 5;
        canvas.drawCircle(startX+(oneWidth*x),startY+(oneWidth*y),radius,whiteChessPaint);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        drawChessBoard();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
}
