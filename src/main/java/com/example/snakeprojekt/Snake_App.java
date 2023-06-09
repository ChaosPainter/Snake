package com.example.snakeprojekt;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Snake_App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Snake_App.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        Controller c= fxmlLoader.getController();
        stage.setOnCloseRequest(event->{
                                        event.consume(); // this lambda expression stops the timer created in Controller before the application close
                                        c.onClose();     // as to not leave a background task running indefinitely
                                        stage.close();
                                        }
                               );
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}