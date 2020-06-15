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
    @FXML private TextField type;
    @FXML private TextField url;
    @FXML private DatePicker date;
    @FXML private ComboBox start;
    @FXML private ComboBox end;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    private ObservableList<String> startTimesList = FXCollections.observableArrayList("08:00 AM", "08:30 AM", "09:00 AM",
            "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "01:30 PM", "2:00 PM",
            "02:30 PM", "03:00 PM");

    private ObservableList<String> meetingTypes = FXCollections.observableArrayList("First", "Follow-Up", "Final");

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

        }
    }

    public void handleSave(ActionEvent event) throws ParseException {
        try {

            LocalDate dateOnly = date.getValue();
            String startTimeSelected = startTimesList.get(start.getSelectionModel().getSelectedIndex());
            DateFormat inputFormat = new SimpleDateFormat("hh:mm aa");
            DateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
            String timeOnly = outputFormat.format(inputFormat.parse(startTimeSelected));
            String dateTime = dateOnly + " " + timeOnly;

            // Adds 30 min to the time to calculate for appointment end time
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");
            Date d = df.parse(startTimeSelected);
            Calendar cal = Calendar.getInstance();
            cal.setTime(d);
            cal.add(Calendar.MINUTE, 30);
            String newTime = df.format(cal.getTime());
            String endDateTime = dateOnly + " " + newTime + ":00";

            // Get Current Time
            final Date currentTime = new Date();
            final SimpleDateFormat formattedDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate.setTimeZone(TimeZone.getTimeZone("GMT"));
            String currentDateTime = formattedDate.format(currentTime);


            try {
                Statement statement = Database.getStatement();
                String insertQuery = "INSERT IGNORE INTO appointment (customerId, userId, title, description, location," +
                        "contact, type, url, start, end, createDate, createdBy) VALUES ('" + customerId.getText() + "', '" + userId.getText() + "', '" +
                        title.getText() + "', '" + description.getText() + "', '" + location.getText() + "', '" + contact.getText() + "', '" +
                        type.getText() + "', '" + url.getText() + "', '" + dateTime + "', '" + endDateTime + "', '" +
                        currentDateTime + "', '" + docController.getUser().getUsername() + "')";
                Boolean insertResult = statement.execute(insertQuery);


                // Select Appointment
                String selectQuery = "SELECT * FROM appointment WHERE appointmentId = '" + appointmentId.getText() + "'";
                ResultSet result = statement.executeQuery(selectQuery);
                result.next();
                Appointment appointment = new Appointment();
                appointment.setAppointmentId(result.getString(1));
                appointment.setCustomerId(result.getString(2));
                appointment.setUserId(result.getString(3));
                appointment.setTitle(result.getString(4));
                appointment.setDescription(result.getString(5));
                appointment.setLocation(result.getString(6));
                appointment.setContact(result.getString(7));
                appointment.setType(result.getString(8));
                appointment.setUrl(result.getString(9));
                appointment.setStart(result.getString(10));
                appointment.setEnd(result.getString(11));
                appointment.setCreateDate(result.getString(12));
                appointment.setCreatedBy(result.getString(13));
                appointment.setStart(result.getString(14));
                docController.getAppointmentList().add(appointment);

                final Node previous = (Node) event.getSource();
                final Stage stage = (Stage) previous.getScene().getWindow();
                stage.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setItems(startTimesList);
    }

}
