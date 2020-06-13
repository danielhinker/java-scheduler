package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.User;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;
import javafx.fxml.FXML;

public class LoginController implements Initializable {

    private static User currentUser;

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;

    public static User getCurrentUser() {
        return currentUser;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleLogin(ActionEvent actionEvent) throws SQLException {

//        System.out.println(usernameField.getText());
//        try {
            Statement statement = Database.queryStatement();
            System.out.println((statement));
            String query = "SELECT * FROM user WHERE userName='" + usernameField.getText() + "' AND password='" + passwordField.getText() + "'";
            ResultSet results = statement.executeQuery(query);
            if (results.next()) {
//                currentUser = new User();
//                currentUser.setUsername(results.getString("userName"));
//                statement.close();
////                Logger.log(username, true);
                System.out.println(results.next());
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
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
