package com.example.snakeprojekt;

import javafx.scene.paint.Color;

public class SnakeSegment {

    private int posx;
    private int posy;
    private Color color;
    public static final int seg_width=20;
    public static final int  seg_hight=20;

    public Color getColor() {
        return color;
    }

    public void moveSegment(int move_by_X, int move_by_Y)
    {
        this.posx+=move_by_X;
        this.posy+=move_by_Y;
    }
    public boolean isIntersecting(SnakeSegment seg)
    {
        if (seg.getPosx()==this.posx && seg.getPosy()==this.posy)
        {
            return true;
        }
        return false;
    }

    public SnakeSegment(int posx, int posy, Color c) {
        this.posx = posx;
        this.posy = posy;
        this.color = c;
    }

    public int getPosx() {
        return posx;
    }

    public void setPosx(int posx) {
        this.posx = posx;
    }

    public int getPosy() {
        return posy;
    }

    public void setPosy(int posy) {
        this.posy = posy;
    }
}
