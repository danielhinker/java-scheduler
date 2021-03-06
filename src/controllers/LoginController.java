package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
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
import java.util.Locale;
import java.util.ResourceBundle;
import java.io.File;

public class LoginController implements Initializable {

    @FXML private TextField usernameField;
    @FXML private TextField passwordField;
    @FXML private Button loginButton;
    @FXML private Text title;
    @FXML private Text passwordText;
    @FXML private Text usernameText;

    private User user;
    public User getUser() {
        return user;
    }

    Alert incorrectLogin = new Alert(Alert.AlertType.INFORMATION);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Locale defaultLanguage = Locale.getDefault();
        resources = ResourceBundle.getBundle("language", defaultLanguage);
        incorrectLogin.setTitle(resources.getString("errorTitle"));
        incorrectLogin.setHeaderText(resources.getString("header"));
        loginButton.setText(resources.getString("loginButton"));
        title.setText(resources.getString("title"));
        passwordText.setText(resources.getString("passwordText"));
        usernameText.setText(resources.getString("usernameText"));

    }

    public void handleLogin(ActionEvent actionEvent) throws SQLException {

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

                    String currentTime = Utilities.getCurrentDateTime();

                    Statement statement2 = Database.getStatement();
                    String query2 = "SELECT * FROM appointment WHERE '" + currentTime + "' >= start - INTERVAL 15 MINUTE AND '" + currentTime + "' <= start";

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

            } else {
                incorrectLogin.show();
            }

    }

}
