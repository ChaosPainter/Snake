package com.example.snakeprojekt;
import java.sql.*;

public class DBConnector {

    private static String url = "jdbc:postgresql://localhost:5432/Snake";
    private static String user = "postgres";
    private static String pass = "password1";

    public static Connection connect() {
        Connection conn = null;
        try {

            conn = DriverManager.getConnection(url, user, pass);
            if (conn != null) {
                System.out.println("Connection established");
            } else {
                System.out.println("Connection failed");
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return conn;
    }
}
