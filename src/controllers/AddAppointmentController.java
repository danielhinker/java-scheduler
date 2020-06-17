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
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
//    @FXML private TextField type;
    @FXML private TextField url;
    @FXML private DatePicker date;
    @FXML private ComboBox start;
    @FXML private ComboBox end;
    @FXML private ComboBox type;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    private ObservableList<String> startTimesList = FXCollections.observableArrayList("08:00 AM", "08:30 AM", "09:00 AM",
            "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "01:30 PM", "2:00 PM",
            "02:30 PM", "03:00 PM");

    private ObservableList<String> endTimesList = FXCollections.observableArrayList("15 Minutes", "30 Minutes", "45 Minutes", "60 Minutes");

    private ObservableList<String> meetingTypes = FXCollections.observableArrayList("In Person", "Phone", "Video");

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void checkAppointment(String dateTime) {
        try {
            ObservableList<Appointment> appointmentTimesList = FXCollections.observableArrayList();

            Statement statement = Database.getStatement();
            String insertQuery = "SELECT * from appointment";
            ResultSet selectResult = statement.executeQuery(insertQuery);
            while (selectResult.next()) {
                Appointment appointment = new Appointment();
                appointment.setStart(selectResult.getString(10));
                appointment.setEnd(selectResult.getString(11));
                appointmentTimesList.add(appointment);
            }

//            String startTime = selectResult.getString(10);
//            String endTime = selectResult.getString(11);

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public void handleSave(ActionEvent event) throws ParseException, SQLException {


        LocalDate dateOnly = date.getValue();
        String startTimeSelected = startTimesList.get(start.getSelectionModel().getSelectedIndex());
        DateFormat inputFormat = new SimpleDateFormat("hh:mm aa");
        DateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
        String timeOnly = outputFormat.format(inputFormat.parse(startTimeSelected));
        String dateTime = dateOnly + " " + timeOnly;

        // Adds 30 min to the time to calculate for appointment end time
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


            Statement statement = Database.getStatement();
            String insertQuery = "INSERT IGNORE INTO appointment (customerId, userId, title, description, location," +
                    "contact, type, url, start, end, createDate, createdBy) VALUES ('" + customerId.getText() + "', '" + userId.getText() + "', '" +
                    title.getText() + "', '" + description.getText() + "', '" + location.getText() + "', '" + contact.getText() + "', '" +
                    typeSelected + "', '" + url.getText() + "', '" + dateTime + "', '" + endDateTime + "', '" +
                    currentDateTime + "', '" + docController.getUser().getUsername() + "')";
            Boolean insertResult = statement.execute(insertQuery);


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
