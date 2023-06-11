package com.example.snakeprojekt;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class Controller implements Initializable {

    @FXML
    private Canvas MainCanvas;
    private GraphicsContext gc;

    private int speed=150;

    private boolean pause =true;
    private boolean start=false;

    private boolean key_flag=false;

    private boolean game_over=false;
    int points=0;
    Snake snake= new Snake();
    Food food = null;
    Timer timer = new Timer();
    TimerTask task_run = new TimerTask() {
        @Override
        public void run()
        {
            //System.out.println(System.currentTimeMillis());
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
                key_flag=false;

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

                if (game_over)
                {
                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            TextInputDialog dialog = new TextInputDialog();
                            dialog.setTitle("GAME OVER");
                            dialog.setHeaderText("GAME OVER");
                            dialog.setContentText("Please enter your name:");
                            Optional<String> result = dialog.showAndWait();
                            if (result.isPresent())
                            {
                                //System.out.println(result.get());
                                add_game_result_to_DB(result.get());
                            }
                        }
                    });
                }
                gc.setStroke(Color.WHITE);
                gc.strokeText("Points: "+points,10,10);





            }
        }
    };
//    TimerTask task_stop=new TimerTask() {
//        @Override
//        public void run() {
//
//        }
//    };

//    @FXML
//    void moveSquareKeyPressed(KeyEvent event)
//    {
//        System.out.println("kuna");
//    }

    private void add_game_result_to_DB(String name_)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        String date = now.format(dtf);
        String sql="Insert Into results (name, points,date) Values ('"+name_+"',"+String.valueOf(points)+",'"+date+"');";
        DBExecutor.executeQuery(sql);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        snake.add_Head();
        timer.scheduleAtFixedRate(task_run, 0, speed);
        gc = MainCanvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.strokeText("Press 'P' to Start or Pause the game.",210,250);

//        MainCanvas.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                if (!start)
//                {
//                    timer.scheduleAtFixedRate(task_run, 0, speed);
//                    start=true;
//                }
//            }
//        });
        MainCanvas.setFocusTraversable(true);
        MainCanvas.addEventFilter(KeyEvent.KEY_RELEASED, new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent keyEvent) {

                if (keyEvent.getCode() == KeyCode.W && snake.getDir()!=Direction.DOWN && key_flag==false) {
                    snake.dir = Direction.UP;
                    key_flag=true;
                }
                if (keyEvent.getCode() == KeyCode.A && snake.getDir()!=Direction.RIGHT && key_flag==false) {
                    snake.dir = Direction.LEFT;
                    key_flag=true;
                }
                if (keyEvent.getCode() == KeyCode.S && snake.getDir()!=Direction.UP && key_flag==false) {
                    snake.dir = Direction.DOWN;
                    key_flag=true;
                }
                if (keyEvent.getCode() == KeyCode.D && snake.getDir()!=Direction.LEFT && key_flag==false) {
                    snake.dir = Direction.RIGHT;
                    key_flag=true;
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

    public void restart ()
    {
        snake=new Snake();
        snake.add_Head();
        points=0;
        food=null;
        game_over=false;

    }

    public void about ()
    {
        System.out.println("javafx.runtime.version: " + System.getProperty("javafx.runtime.version"));
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Snake Game");
        alert.setContentText("This is a recreation of a classic Snake game using JavaFX.\n" +
                            "Author: W.L");
        alert.showAndWait();
    }

    public void top_10_scores () throws SQLException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("TOP Scores");
        alert.setHeaderText("Top 10 Scores");
        String sql="Select * From results Order By points DESC;";
        ResultSet resultSet = DBExecutor.executeSelect(sql);
        String res="";
        int i=1;
        while (resultSet.next() && i<=10)
        {
            res+=i;
            res+=" ";
            res+=resultSet.getString("name");
            res+=" ";
            res+=resultSet.getInt("points");
            res+=" ";
            res+=resultSet.getString("date");
            res+="\n";
            i++;

        }
        alert.setContentText(res);

        alert.showAndWait();
    }
    public void onClose()
    {
        timer.cancel();
    }
}