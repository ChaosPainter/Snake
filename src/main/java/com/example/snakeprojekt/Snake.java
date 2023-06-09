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

    public void add_Segment()
    {
        int posX=snakeBody.get(snakeBody.size()-1).getPosx();
        int posY=snakeBody.get(snakeBody.size()-1).getPosy();
        SnakeSegment segment = new SnakeSegment(posX,posY,Color.GREEN);
        snakeBody.add(segment);
    }
    public void add_Head()
    {
        SnakeSegment segment = new SnakeSegment(200,200,Color.RED);
        snakeBody.add(segment);
    }

    public Snake()
    {
        dir=Direction.RIGHT;
    }
    
    public void moveSnake ()
    {
    
    }

}
