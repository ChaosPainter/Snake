module com.example.snakeprojekt {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.snakeprojekt to javafx.fxml;
    exports com.example.snakeprojekt;
}