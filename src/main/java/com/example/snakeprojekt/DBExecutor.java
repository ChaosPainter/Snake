package com.example.snakeprojekt;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DBExecutor {
    public static ResultSet executeSelect(String selectQuery){
        try{
            Connection connection = DBConnector.connect();
            Statement statement = connection.createStatement();
            return statement.executeQuery(selectQuery);
        }
        catch(SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }

    public static void executeQuery(String query){
        try{
            Connection connection = DBConnector.connect();
            Statement statement = connection.createStatement();
            statement.execute(query);
        }
        catch (SQLException e){
            throw new RuntimeException(e.getMessage());
        }

    }
}
