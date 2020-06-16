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

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
//                currentUser = new User();
//                currentUser.setUsername(results.getString("userName"));
//                statement.close();
////                Logger.log(username, true);
//                System.out.println(results.next());
                try {
                    ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Menu.fxml"));
                    Parent root1 = loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    loader.<MenuController>getController().setDocController(this);
                    stage.show();

                } catch (Exception e) {
                    System.out.println((e));
                }
//                return true;
//                changeView();
            } else {
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
