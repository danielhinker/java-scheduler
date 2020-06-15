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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;
import java.time.*;
import javafx.scene.control.*;

public class ModifyAppointmentController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {

        this.docController = docController;
        start.setItems(startTimesList);
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
            type.setText(result.getString(8));
            url.setText(result.getString(9));

            String dateTime = result.getString(10);

            DateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat = new SimpleDateFormat("hh:mm aa");
            String timeOnly = outputFormat.format(inputFormat.parse(dateTime));

            DateFormat inputFormat2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            DateFormat outputFormat2 = new SimpleDateFormat("yyyy-MM-dd");
            String dateOnly = outputFormat2.format(inputFormat2.parse(dateTime));

            LocalDate localDate = LocalDate.parse(dateOnly);

            date.setValue(localDate);

            start.getSelectionModel().select(timeOnly);

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
    @FXML private TextField type;
    @FXML private TextField url;
    @FXML private DatePicker date;
    @FXML private ComboBox start;
    @FXML private ComboBox end;
    @FXML private Button cancelButton;
    @FXML private Button saveButton;

    private final ObservableList<String> startTimesList = FXCollections.observableArrayList("08:00 AM", "08:30 AM", "09:00 AM",
            "9:30 AM", "10:00 AM", "10:30 AM", "11:00 AM", "11:30 AM", "12:00 PM", "12:30 PM", "1:00 PM", "01:30 PM", "2:00 PM",
            "02:30 PM", "03:00 PM");

    private final ObservableList<String> meetingTypes = FXCollections.observableArrayList("First", "Follow-Up", "Final");

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleSave(ActionEvent event) {
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
            String insertQuery = "UPDATE appointment SET customerId = '" + customerId.getText() + "', userId = '" +
                    userId.getText() + "', title = '" + title.getText() + "', description = '" + description.getText()
                    + "', location = '" + location.getText() + "', contact = '" + contact.getText() + "', type = '"
                    + type.getText() + "', url = '" + url.getText() + "', start = '" + dateTime + "', end = '"
                    + endDateTime + "', lastUpdateBy = '"
                    + docController.getUser().getUsername() + "' WHERE appointmentId = '" + appointmentId.getText() + "'";
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
//            appointment.setStart(result.getString(14));
            docController.getAppointmentList().set(docController.appointmentClickedIndex, appointment);

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

    }
}
