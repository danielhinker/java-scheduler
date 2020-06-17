package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Appointment;

import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;
import javafx.scene.control.*;
import models.Utilities;

public class ModifyAppointmentController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {

        this.docController = docController;
        start.setItems(startTimesList);
        end.setItems(endTimesList);
        type.setItems(meetingTypes);

        Statement statement = Database.getStatement();
        String query = "SELECT * from appointment WHERE appointmentId = '" + docController.appointmentClicked.getAppointmentId() + "'";
        ResultSet result;
        try {
            result = statement.executeQuery(query);
            result.next();
            appointmentId.setText(result.getString(1));
            customerId.setText(result.getString(2));
            userId.setText(result.getString(3));
            title.setText(result.getString(4));
            description.setText(result.getString(5));
            location.setText(result.getString(6));
            contact.setText(result.getString(7));
            type.setValue(result.getString(8));

            url.setText(result.getString(9));

            String dateTime = result.getString(10);
            String endDateTime = result.getString(11);

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("hh:mm aa");
            String timeOnly = outputFormat.format(inputFormat.parse(dateTime));

            DateFormat inputFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            String dateOnly = outputFormat2.format(inputFormat2.parse(dateTime));

            LocalDate localDate = LocalDate.parse(dateOnly);
            
            date.setValue(localDate);

            start.getSelectionModel().select(timeOnly);

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            Date startDate = null;
            Date endDate = null;

            int differenceMinutes = 0;
            try {
                startDate = format.parse(dateTime);
                endDate = format.parse(endDateTime);

                long timeDifference = endDate.getTime() - startDate.getTime();
                long diffMinutes = timeDifference / (60 * 1000) % 61;

                differenceMinutes = (int) diffMinutes;

            } catch (Exception e) {
                e.printStackTrace();
            }
            end.setValue(differenceMinutes + " Minutes");
            appointmentId.setDisable(true);
        } catch (SQLException | ParseException e) {
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
    @FXML private ComboBox type;
    @FXML private ComboBox start;
    @FXML private ComboBox end;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    private final ObservableList<String> startTimesList = FXCollections.observableArrayList("08:00 AM", "08:15 AM",
            "08:30 AM", "08:45 AM", "09:00 AM", "09:15 AM", "09:30 AM", "09:45 AM", "10:00 AM", "10:15 AM", "10:30 AM",
            "10:45 AM", "11:00 AM", "11:15 AM", "11:30 AM", "11:45 AM", "12:00 PM", "12:15 PM", "12:30 PM", "12:45 PM",
            "01:00 PM", "01:15 PM", "01:30 PM", "01:45 PM", "02:00 PM", "02:15 PM", "02:30 PM", "02:45 PM", "03:00 PM");

    private ObservableList<String> endTimesList = FXCollections.observableArrayList("15 Minutes", "30 Minutes",
            "45 Minutes", "60 Minutes");

    private ObservableList<String> meetingTypes = FXCollections.observableArrayList("In Person", "Phone", "Video");

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleSave(ActionEvent event) throws ParseException, SQLException {
        LocalDate dateOnly = date.getValue();
        String startTimeSelected = startTimesList.get(start.getSelectionModel().getSelectedIndex());
        DateFormat inputFormat = new SimpleDateFormat("hh:mm aa");
        DateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
        String timeOnly = outputFormat.format(inputFormat.parse(startTimeSelected));
        String dateTime = dateOnly + " " + timeOnly;

        // Sets End Time
        SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        Date d = df.parse(timeOnly);
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
        String newTime = df.format(cal.getTime());
        String endDateTime = dateOnly + " " + newTime;
//            System.out.println(newTime);
        String currentDateTime = Utilities.getCurrentDateTime();
        String typeSelected = meetingTypes.get(type.getSelectionModel().getSelectedIndex());


        // Check for overlapping appointments
        Statement statementQuery = Database.getStatement();
        String selectQuery2 = "SELECT * FROM appointment WHERE (start > '" + dateTime + "' AND start < '" + endDateTime + "')" +
                " OR (end > '" + dateTime + "' AND end < '" + endDateTime + "')";
        ResultSet selectResult2 = statementQuery.executeQuery(selectQuery2);
        Connection connection = Database.getConnection();


        if (selectResult2.next()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Overlapping appointment times.");
            alert.show();
            return;
        } else {

//            try {
                Statement statement = Database.getStatement();
                String insertQuery = "UPDATE appointment SET customerId = '" + customerId.getText() + "', userId = '" +
                        userId.getText() + "', title = '" + title.getText() + "', description = '" + description.getText()
                        + "', location = '" + location.getText() + "', contact = '" + contact.getText() + "', type = '"
                        + typeSelected + "', url = '" + url.getText() + "', start = '" + dateTime + "', end = '"
                        + endDateTime + "', lastUpdate = '" + currentDateTime + "', lastUpdateBy = '"
                        + docController.getUser().getUsername() + "' WHERE appointmentId = '" + appointmentId.getText() + "'";
                statement.execute(insertQuery);


                // Select Appointment
                String selectQuery = "SELECT * FROM appointment WHERE appointmentId = '" + appointmentId.getText() + "'";
                ResultSet result = statement.executeQuery(selectQuery);
                result.next();
                docController.getAppointmentList().set(docController.appointmentClickedIndex, Appointment.setAppointment(result));

                final Node previous = (Node) event.getSource();
                final Stage stage = (Stage) previous.getScene().getWindow();
                stage.close();
    }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
