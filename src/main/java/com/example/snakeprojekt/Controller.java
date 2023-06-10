package com.example.snakeprojekt;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    private Canvas MainCanvas;
    private GraphicsContext gc;
    private boolean pause =false;
    private boolean start=false;

    private boolean game_over=false;
    int points=0;
    Snake snake= new Snake();
    Food food = null;
    Timer timer = new Timer();
    TimerTask task_run = new TimerTask() {
        @Override
        public void run()
        {
            if (!pause && !game_over) {
                gc = MainCanvas.getGraphicsContext2D();
                gc.clearRect(0, 0, MainCanvas.getHeight(), MainCanvas.getWidth());
                gc.setFill(Color.BLACK);
                gc.fillRect(0,0,600,600);
                //generate food if no food
                if (food==null) {
                    do {
                        food=new Food();
                        food.setNewFood();
                    }
                    while (food.isIntersecting(snake));
                }

                //food=new Food(60,60);
                gc.setFill(food.getColor());
                gc.fillOval(food.getPosx()-Food.radius, food.getPosy()-Food.radius,Food.radius*2, Food.radius*2);

                int tail_posX= snake.snakeBody.get(snake.snakeBody.size()-1).getPosx();
                int tail_posY= snake.snakeBody.get(snake.snakeBody.size()-1).getPosy();
                snake.moveSnake();

                if (snake.eatFood(food))
                {
                    food=null;
                    snake.add_Segment(tail_posX, tail_posY);
                    points++;
                }


                for (int i = snake.snakeBody.size()-1; i >=0 ; i--)
                {
                    SnakeSegment s = snake.snakeBody.get(i);
                    gc.setFill(s.getColor());
                    gc.fillPolygon(new double[]{s.getPosx() - SnakeSegment.seg_width, s.getPosx() + SnakeSegment.seg_width, s.getPosx() + SnakeSegment.seg_width, s.getPosx() - SnakeSegment.seg_width},
                            new double[]{s.getPosy() + SnakeSegment.seg_hight, s.getPosy() + SnakeSegment.seg_hight, s.getPosy() - SnakeSegment.seg_hight, s.getPosy() - SnakeSegment.seg_hight},
                            4);
                }

                for (int i = snake.snakeBody.size()-1; i >0 ; i--)
                {
                    if (snake.snakeBody.get(i).isIntersecting(snake.snakeBody.get(0)))
                    {
                        game_over=true;

                    }
                }






            }
        }
    };
    TimerTask task_stop=new TimerTask() {
        @Override
        public void run() {

        }
    };

    @FXML
    void moveSquareKeyPressed(KeyEvent event)
    {
        System.out.println("kuna");
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        snake.add_Head();
        MainCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (!start)
                {
                    timer.scheduleAtFixedRate(task_run, 0, 150);
                    start=true;
                }
            }
        });
        MainCanvas.setFocusTraversable(true);
        MainCanvas.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.W && snake.getDir()!=Direction.DOWN) {
                    snake.dir = Direction.UP;
                }
                if (keyEvent.getCode() == KeyCode.A && snake.getDir()!=Direction.RIGHT) {
                    snake.dir = Direction.LEFT;
                }
                if (keyEvent.getCode() == KeyCode.S && snake.getDir()!=Direction.UP) {
                    snake.dir = Direction.DOWN;
                }
                if (keyEvent.getCode() == KeyCode.D && snake.getDir()!=Direction.LEFT) {
                    snake.dir = Direction.RIGHT;
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    if (pause)
                    {
                        pause=false;
                    }
                    else pause =true;
                }
            }
        });



    }

    public void onClose()
    {
        timer.cancel();
    }
}