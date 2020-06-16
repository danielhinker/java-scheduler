package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AppointmentTypesController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;
    }

    @FXML
    private Button closeButton;
    @FXML
    private Button apply;

    @FXML private Label inPerson;
    @FXML private Label phone;
    @FXML private Label video;

    @FXML private ComboBox month;

    private ObservableList<String> monthList = FXCollections.observableArrayList("January", "February", "March",
            "April", "May", "June", "July", "August", "September", "October", "November"," December");

    public void handleApply(ActionEvent event) throws SQLException {
        String index = Integer.toString(month.getSelectionModel().getSelectedIndex() + 1);
//        System.out.println(index);


        Statement statement = Database.getStatement();
        String videoQuery = "SELECT COUNT(*) AS total FROM appointment WHERE type = 'Video' and MONTH(start) = '" + index + "'";
        ResultSet videoResult = statement.executeQuery(videoQuery);
        videoResult.next();

        video.setText(videoResult.getString("total"));

        String inPersonQuery = "SELECT COUNT(*) AS total FROM appointment WHERE type = 'In Person' and MONTH(start) = '" + index + "'";
        ResultSet inPersonResult = statement.executeQuery(inPersonQuery);
        inPersonResult.next();
        inPerson.setText(inPersonResult.getString("total"));

        String phoneQuery = "SELECT COUNT(*) AS total FROM appointment WHERE type = 'Phone' and MONTH(start) = '" + index + "'";

        ResultSet phoneResult = statement.executeQuery(phoneQuery);

        phoneResult.next();


        phone.setText(phoneResult.getString("total"));
    }


    public void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        month.setItems(monthList);
    }
}
