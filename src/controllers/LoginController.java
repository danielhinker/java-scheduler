package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;
import models.Utilities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

import java.io.File;  // Import the File class


public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

//    private String currentDate;
    private User user;
    public User getUser() {
        return user;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {


    }

    public void handleLogin(ActionEvent actionEvent) throws SQLException {

//        try {
            Statement statement = Database.getStatement();
            System.out.println((statement));
            String query = "SELECT * FROM user WHERE userName='" + usernameField.getText() + "' AND password='" + passwordField.getText() + "'";
            ResultSet results = statement.executeQuery(query);
            if (results.next()) {
                user = new User();
                user.setUserId(results.getString(1));
                user.setUsername(results.getString(2));
                try {
                    File file = new File("login-log.txt");
                    file.createNewFile();
                    PrintWriter myWriter = new PrintWriter(new FileWriter("login-log.txt", true));
                    myWriter.write("UserID: '" + user.getUserId() + "', Username: '" + user.getUsername()
                            + "', Login Time: '" + Utilities.getCurrentDateTime() + "'\n");
                    myWriter.close();
                } catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Menu.fxml"));
                    Parent root1 = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    loader.<MenuController>getController().setDocController(this);
                    stage.show();

                    Statement statement2 = Database.getStatement();
                    String query2 = "SELECT * FROM appointment WHERE NOW() > start - INTERVAL 15 MINUTE AND NOW() < start";

//                   START = 11:15am  NOW() = 17:55   now() - INTERVAL 15 min = 17:40

                    try {
                        ResultSet result2 = statement2.executeQuery(query2);
                        if (result2.next()) {
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Error");
                            alert.setHeaderText("Appointment starts within 15 minutes");
                            alert.show();
                        }
                    } catch (SQLException e) {
                        System.out.println(e);
                    }

                } catch (Exception e) {
                    System.out.println((e));
                }
//                return true;
//                changeView();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText("Incorrect Username or Password");
                alert.show();
//                System.out.println("failed");
//                Logger.log(username, false);
//                return false;
            }
//
//
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
//        } catch (SQLException throwables) {
//            throwables.printStackTrace();
//        }
    }

//        public static void changeView() {
//        try {
//        FXMLLoader loader = new FXMLLoader(LoginController.class.getResource("Menu.fxml"));
//        Parent root1 = loader.load();
//        Stage stage = new Stage();
//        stage.setScene(new Scene(root1));
////        loader.<MenuController>getController().setDocController(this);
//        stage.show();
//    } catch (Exception e) {
//        System.out.println((e));
//    }
//    }


}
