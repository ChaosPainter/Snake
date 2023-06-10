package com.example.snakeprojekt;

import javafx.scene.paint.Color;

import java.util.Random;

public class Food {
    private int posx;
    private int posy;
    public static final int radius=20;
    private Color color = Color.BLUE;
    public void setNewFood ()
    {
        Random random = new Random();
        this.setPosx((random.nextInt(14))*40+20);
        this.setPosy((random.nextInt(14))*40+20);
        Color col=new Color(random.nextFloat(),random.nextFloat(),random.nextFloat(),1);
        this.setColor(Color.BLUE);
    }

    public Food()
    {

    }

    public Food(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
    }

    public boolean isIntersecting (Snake snake)
    {
        for (SnakeSegment segment: snake.snakeBody)
        {
            if (this.getPosy()==segment.getPosy() && this.getPosx()== segment.getPosx())
            {
                return true;
            }

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
