package com.example.snakeprojekt;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    private Canvas MainCanvas;
    private GraphicsContext gc;


    int points=0;
    Snake snake= new Snake();
    Food food = new Food();
    Timer timer = new Timer();
    TimerTask task = new TimerTask() {
        @Override
        public void run()
        {
            gc = MainCanvas.getGraphicsContext2D();

            for (SnakeSegment s:snake.snakeBody)
            {
                gc.setFill(s.getColor());
                gc.fillPolygon(new double[]{s.getPosx()-SnakeSegment.seg_width,s.getPosx()+SnakeSegment.seg_width,s.getPosx()+SnakeSegment.seg_width,s.getPosx()-SnakeSegment.seg_width},
                               new double[]{s.getPosy()+SnakeSegment.seg_hight, s.getPosy()+SnakeSegment.seg_hight,s.getPosy()-SnakeSegment.seg_hight, s.getPosy()-SnakeSegment.seg_hight},
                                4);
            }

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
        timer.scheduleAtFixedRate(task,0, 33);
        MainCanvas.setFocusTraversable(true);
        MainCanvas.addEventFilter(KeyEvent.KEY_PRESSED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.W) {
                    snake.dir = Direction.UP;
                }
                if (keyEvent.getCode() == KeyCode.A) {
                    snake.dir = Direction.LEFT;
                }
                if (keyEvent.getCode() == KeyCode.S) {
                    snake.dir = Direction.DOWN;
                }
                if (keyEvent.getCode() == KeyCode.D) {
                    snake.dir = Direction.RIGHT;
                }
                if (keyEvent.getCode() == KeyCode.P) {
                    System.out.println("P");
                }
            }
        });



    }

    public void onClose()
    {
        timer.cancel();
    }
}