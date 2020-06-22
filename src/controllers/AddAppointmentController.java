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

    public void validateDate(String dateTime) throws Exception {
        SimpleDateFormat date2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d2 = date2.parse(dateTime);
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(d2);
        int dayOfWeek = cal2.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == 1 || dayOfWeek == 7) {
            throw new Exception();
        }
//        System.out.println(dayOfWeek);
    }

    public void handleSave(ActionEvent event) throws ParseException, SQLException {

//   sets appt time as localtime
        LocalDate dateOnly = date.getValue();
        String startTimeSelected = startTimesList.get(start.getSelectionModel().getSelectedIndex());
        DateFormat inputFormat = new SimpleDateFormat("hh:mm aa");
        DateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
        String timeOnly = outputFormat.format(inputFormat.parse(startTimeSelected));

        String startDateTime = dateOnly + " " + timeOnly;

        try {
            validateDate(startDateTime);
        } catch (Exception e) {
//            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Appointment time and day is outside of business hours.");
            alert.show();
            return;
        }
//        System.out.println(startDateTime);


//         Converts appt time to UTC
        SimpleDateFormat startDateTimeFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startDateTimeFormatUTC.setTimeZone(TimeZone.getDefault());
        Date date = startDateTimeFormatUTC.parse(startDateTime);
        startDateTimeFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        String startDateTimeUTC = startDateTimeFormatUTC.format(date);
//        System.out.println(startDateTimeUTC);


        // Sets End Time as localtime
        SimpleDateFormat endTimeFormat = new SimpleDateFormat("HH:mm:ss");
        Date parsed = endTimeFormat.parse(timeOnly);
        Calendar cal = Calendar.getInstance();
        cal.setTime(parsed);
        if (end.getSelectionModel().getSelectedIndex() == 0) {
            cal.add(Calendar.MINUTE, 15);
        } else if (end.getSelectionModel().getSelectedIndex() == 1) {
            cal.add(Calendar.MINUTE, 30);
        } else if (end.getSelectionModel().getSelectedIndex() == 2) {
            cal.add(Calendar.MINUTE, 45);
        } else {
            cal.add(Calendar.MINUTE, 60);
        }


        String newTime = endTimeFormat.format(cal.getTime());
        String endDateTime = dateOnly + " " + newTime;

//        // Sets endtime to UTC
        SimpleDateFormat endDateTimeFormatUTC = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        endDateTimeFormatUTC.setTimeZone(TimeZone.getDefault());
        Date parsedEndTime = endDateTimeFormatUTC.parse(endDateTime);
        endDateTimeFormatUTC.setTimeZone(TimeZone.getTimeZone("UTC"));
        String endDateTimeUTC = endDateTimeFormatUTC.format(parsedEndTime);

        String currentDateTime = Utilities.getCurrentDateTime();
        String typeSelected = meetingTypes.get(type.getSelectionModel().getSelectedIndex());


//        // Check for overlapping appointments
        System.out.println(startDateTimeUTC);
        System.out.println(endDateTimeUTC);
            Statement statementQuery = Database.getStatement();
            String selectQuery2 = "SELECT * FROM appointment WHERE ('" + startDateTimeUTC + "' >= start AND '" + endDateTimeUTC + "' <= end) OR" +
                    "('" + startDateTimeUTC + "' <= start AND '" + endDateTimeUTC + "' > start AND '" + endDateTimeUTC + "' < end) OR" +
                    "('" + startDateTimeUTC + "' > start AND '" + endDateTimeUTC + "' >= end AND '" + startDateTimeUTC + "' < end)";
//        String selectQuery2 = "SELECT * FROM appointment WHERE (start <= '" + startDateTimeUTC + "' AND end >= '" + endDateTimeUTC + "')" +
//                "OR (start >= '" + startDateTimeUTC + "' AND end <= '" + endDateTimeUTC + "')" +
//                "OR (start >= '" + startDateTimeUTC + "' AND end <= '" + endDateTimeUTC + "')";
            ResultSet selectResult2 = statementQuery.executeQuery(selectQuery2);
//
        System.out.println(selectResult2.next());
//        if (selectResult2.next()) {
//            Alert alert = new Alert(Alert.AlertType.INFORMATION);
//            alert.setTitle("Error");
//            alert.setHeaderText("Overlapping appointment times.");
//            alert.show();
//            return;
//        } else {
//
//
//            Statement statement = Database.getStatement();
//            String insertQuery = "INSERT IGNORE INTO appointment (customerId, userId, title, description, location," +
//                    "contact, type, url, start, end, createDate, createdBy) VALUES ('" + customerId.getText() + "', '" + userId.getText() + "', '" +
//                    title.getText() + "', '" + description.getText() + "', '" + location.getText() + "', '" + contact.getText() + "', '" +
//                    typeSelected + "', '" + url.getText() + "', '" + startDateTimeUTC + "', '" + endDateTimeUTC + "', '" +
//                    currentDateTime + "', '" + docController.getUser().getUsername() + "')";
//            statement.execute(insertQuery);
//
//
//            // Select Appointment
//            String selectQuery = "SELECT * FROM appointment WHERE appointmentId = '" + appointmentId.getText() + "'";
//            ResultSet result = statement.executeQuery(selectQuery);
//            result.next();
//            Appointment appointment = Appointment.setAppointment(result);
//            docController.getAppointmentList().add(appointment);
//
//            final Node previous = (Node) event.getSource();
//            final Stage stage = (Stage) previous.getScene().getWindow();
//            stage.close();
//        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setItems(startTimesList);
        end.setItems(endTimesList);
    }

}
