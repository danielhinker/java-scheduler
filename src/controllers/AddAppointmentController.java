package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import models.Appointment;
import models.Utilities;

import java.sql.Connection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

public class AddAppointmentController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {

        this.docController = docController;
        type.setItems(meetingTypes);
        customerId.setText(docController.customerClicked.getCustomerId());
        userId.setText(docController.getUser().getUserId());
        Statement statement = Database.getStatement();
        String query = "SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.TABLES\n" +
                "WHERE TABLE_SCHEMA = \"U077EG\"\n" +
                "AND TABLE_NAME = \"appointment\";";
        ResultSet result;
        try {
            result = statement.executeQuery(query);
            result.next();
            appointmentId.setText(result.getString(1));
            appointmentId.setDisable(true);
        } catch (SQLException e) {
            System.out.println(e);
        }
        customerId.setDisable(true);
        userId.setDisable(true);
    }

    @FXML private TextField appointmentId;
    @FXML private TextField customerId;
    @FXML private TextField userId;
    @FXML private TextField title;
    @FXML private TextField description;
    @FXML private TextField location;
    @FXML private TextField contact;
    @FXML private TextField url;
    @FXML private DatePicker date;
    @FXML private ComboBox start;
    @FXML private ComboBox end;
    @FXML private ComboBox type;
    @FXML private Button cancelButton;

    private ObservableList<String> startTimesList = FXCollections.observableArrayList("08:00 AM", "08:15 AM",
            "08:30 AM", "08:45 AM", "09:00 AM", "09:15 AM", "09:30 AM", "09:45 AM", "10:00 AM", "10:15 AM", "10:30 AM",
            "10:45 AM", "11:00 AM", "11:15 AM", "11:30 AM", "11:45 AM", "12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM",
            "01:00 PM", "01:15 PM", "01:30 PM", "01:45 PM", "02:00 PM", "02:15 PM", "02:30 PM", "02:45 PM", "03:00 PM");

    private ObservableList<String> endTimesList = FXCollections.observableArrayList("15 Minutes", "30 Minutes", "45 Minutes", "60 Minutes");

    private ObservableList<String> meetingTypes = FXCollections.observableArrayList("In Person", "Phone", "Video");

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleSave(ActionEvent event) throws ParseException, SQLException {


//   sets appt time as localtime
        LocalDate dateOnly = date.getValue();
        String startTimeSelected = startTimesList.get(start.getSelectionModel().getSelectedIndex());
        DateFormat inputFormat = new SimpleDateFormat("hh:mm aa");
        DateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
        String timeOnly = outputFormat.format(inputFormat.parse(startTimeSelected));

        String dateTime = dateOnly + " " + timeOnly;

        System.out.println(dateTime);

        // Converts appt time to UTC
        String dateStr = "2020-06-17 10:30:00"; // replace with dateTime
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df.setTimeZone(TimeZone.getDefault());
        Date date = df.parse(dateTime);
        df.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate = df.format(date);
        System.out.println(formattedDate);

//
        // Sets End Time as localtime
        SimpleDateFormat df2 = new SimpleDateFormat("HH:mm:ss");
        Date d = df2.parse(timeOnly);
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
        if (end.getSelectionModel().getSelectedIndex() == 0) {
            cal.add(Calendar.MINUTE, 15);
        } else if (end.getSelectionModel().getSelectedIndex() == 1) {
            cal.add(Calendar.MINUTE, 30);
        } else if (end.getSelectionModel().getSelectedIndex() == 2) {
            cal.add(Calendar.MINUTE, 45);
        } else {
            cal.add(Calendar.MINUTE, 60);
        }
        String newTime = df2.format(cal.getTime());
        String endDateTime = dateOnly + " " + newTime;

        // Sets endtime to UTC
        SimpleDateFormat df3 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        df3.setTimeZone(TimeZone.getDefault());
        Date date3 = df3.parse(endDateTime);
        df3.setTimeZone(TimeZone.getTimeZone("UTC"));
        String formattedDate3 = df3.format(date3);
        System.out.println(formattedDate3);


        String currentDateTime = Utilities.getCurrentDateTime();
        String typeSelected = meetingTypes.get(type.getSelectionModel().getSelectedIndex());


//        // Check for overlapping appointments
            Statement statementQuery = Database.getStatement();
            String selectQuery2 = "SELECT * FROM appointment WHERE (start >= '" + formattedDate + "' AND start < '" + formattedDate3 + "')" +
                    " OR (end > '" + formattedDate + "' AND end <= '" + formattedDate3 + "')";
            ResultSet selectResult2 = statementQuery.executeQuery(selectQuery2);
//
        if (selectResult2.next()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Overlapping appointment times.");
            alert.show();
            return;
        } else {


            Statement statement = Database.getStatement();
            String insertQuery = "INSERT IGNORE INTO appointment (customerId, userId, title, description, location," +
                    "contact, type, url, start, end, createDate, createdBy) VALUES ('" + customerId.getText() + "', '" + userId.getText() + "', '" +
                    title.getText() + "', '" + description.getText() + "', '" + location.getText() + "', '" + contact.getText() + "', '" +
                    typeSelected + "', '" + url.getText() + "', '" + formattedDate + "', '" + formattedDate3 + "', '" +
                    currentDateTime + "', '" + docController.getUser().getUsername() + "')";
            statement.execute(insertQuery);


            // Select Appointment
            String selectQuery = "SELECT * FROM appointment WHERE appointmentId = '" + appointmentId.getText() + "'";
            ResultSet result = statement.executeQuery(selectQuery);
            result.next();
            Appointment appointment = Appointment.setAppointment(result);
            docController.getAppointmentList().add(appointment);

            final Node previous = (Node) event.getSource();
            final Stage stage = (Stage) previous.getScene().getWindow();
            stage.close();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setItems(startTimesList);
        end.setItems(endTimesList);
    }

}
