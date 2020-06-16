package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ActivityReportController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;
        Statement statement = Database.getStatement();
        String query = "SELECT COUNT(*) AS total FROM customer";
        String totalCount = null;
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                totalCount = results.getString("total");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        total.setText(totalCount);

        query = "SELECT COUNT(*) AS total FROM customer WHERE active = 1";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                totalCount = results.getString("total");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        active.setText(totalCount);

        query = "SELECT COUNT(*) AS total FROM customer WHERE active = 0";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                totalCount = results.getString("total");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        inactive.setText(totalCount);

    }

    @FXML
    private Button closeButton;

    @FXML private Label total;
    @FXML private Label active;
    @FXML private Label inactive;

    public void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
