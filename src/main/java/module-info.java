module com.example.snakeprojekt {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.snakeprojekt to javafx.fxml;
    exports com.example.snakeprojekt;
}