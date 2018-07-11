package com.example.fivechess.chessgame;

/**
 * 坐标类
 *
 * Created by 张佳欣 on 2018/7/11.
 */
public class Coordinate {

    int x;
    int y;

    public Coordinate(){

    }

    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Coordinate(Coordinate c){
        this.x = c.getX();
        this.y = c.getY();
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
