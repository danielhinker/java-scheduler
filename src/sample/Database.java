package sample;

import java.sql.*;

public class Database {


    private static Statement statement;

//    public static void main(String[] args) throws Exception {
//
//    }

    public Database() {}


    public static Statement queryStatement()  {
        return statement;
    }

    public static void initialize() {


        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:mysql://3.227.166.251:3306/U077EG", "U077EG", "53688956054");
            statement = connection.createStatement();
//            java.util.Date dt = new java.util.Date();

//            java.text.SimpleDateFormat sdf =
//                    new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//            String currentTime = sdf.format(dt);

//            stmt.executeUpdate(
//                    "INSERT INTO user (userName, password, active, createDate, createdBy, lastUpdate, lastUpdateBy) VALUES ('test', 'test1', '1', currentTime, 'me', currentTime, 'me' )"
//            );

//            stmt.executeUpdate("INSERT INTO species VALUES (1, 'African Elephant', 7.5)");
//            stmt.executeUpdate("INSERT INTO species VALUES (2, 'Zebra', 1.2)");
//
//            stmt.executeUpdate("INSERT INTO animal VALUES (1, 1, 'Elsa', '2001−05−06 02:15')");
//            stmt.executeUpdate("INSERT INTO animal VALUES (2, 2, 'Zelda', '2002−08−15 09:12')");
//            stmt.executeUpdate("INSERT INTO animal VALUES (3, 1, 'Ester', '2002−09−09 10:36')");
//            stmt.executeUpdate("INSERT INTO animal VALUES (4, 1, 'Eddie', '2010−06−08 01:24')");
//            stmt.executeUpdate("INSERT INTO animal VALUES (5, 2, 'Zoe', '2005−11−12 03:44')");
        } catch (SQLException | ClassNotFoundException throwables) {
            throwables.printStackTrace();
        }
    }
}