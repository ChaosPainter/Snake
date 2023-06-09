package com.example.snakeprojekt;

import javafx.scene.paint.Color;

import java.util.Random;

public class Food {
    private int posx;
    private int posy;
    public static final int radius=25;
    private Color color;
    public void setNewFood ()
    {

    }

    public Food() {
    }

    public boolean isIntersecting (SnakeSegment snake)
    {
        if (this.getPosy()==snake.getPosy() && this.getPosx()== snake.getPosx())
        {
            return true;
        }
        return false;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
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
