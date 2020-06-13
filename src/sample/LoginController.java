package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void handleLogin() {
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
    }
}
