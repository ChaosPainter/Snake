package com.example.snakeprojekt;

import javafx.scene.paint.Color;

import java.util.ArrayList;

public class Snake {
    ArrayList<SnakeSegment> snakeBody= new ArrayList<>();
    Direction dir;

    public Direction getDir() {
        return dir;
    }

    public void setDir(Direction dir) {
        this.dir = dir;
    }

    public void add_Segment(int posX,int posY)
    {

        SnakeSegment segment = new SnakeSegment(posX,posY,Color.GREEN);
        snakeBody.add(segment);
    }
    public void add_Head()
    {
        SnakeSegment segment = new SnakeSegment(20,60,Color.RED);
        snakeBody.add(segment);
    }

    public Snake()
    {
        dir=Direction.RIGHT;
    }
    
    public void moveSnake ()
    {


        for (int i = snakeBody.size()-1; i >0 ; i--)
        {
            snakeBody.get(i).setPosx(snakeBody.get(i-1).getPosx());
            snakeBody.get(i).setPosy(snakeBody.get(i-1).getPosy());
        }

            switch (dir)
            {
                case UP -> snakeBody.get(0).moveSegment(0,-40);
                case DOWN -> snakeBody.get(0).moveSegment(0,40);
                case RIGHT -> snakeBody.get(0).moveSegment(40,0);
                case LEFT -> snakeBody.get(0).moveSegment(-40,0);
            }
    }

    public boolean eatFood (Food food)
    {
        if (food.getPosx()==snakeBody.get(0).getPosx() && food.getPosy()==snakeBody.get(0).getPosy())
        {
            return true;
        }
        return false;
    }


}
