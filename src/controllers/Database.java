package controllers;

import java.sql.*;

public class Database {


    private static Statement statement;

    private static Connection connection;

//    public static void main(String[] args) throws Exception {
//
//    }

    public Database() {}

    public static Statement getStatement()  {
        return statement;
    }
    public static Connection getConnection() { return connection; }

    public static void initialize() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://3.227.166.251:3306/U077EG", "U077EG", "53688956054");
            statement = connection.createStatement();

        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}