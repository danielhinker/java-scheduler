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

    public void handleSave(ActionEvent event) throws ParseException {
//        try {

            LocalDate dateOnly = date.getValue();

            String startTimeSelected = startTimesList.get(start.getSelectionModel().getSelectedIndex());
//            String timeOnly = startTimeSelected.substring(0, startTimeSelected.length() - 2);
//            String dateTime = dateOnly + " " + timeOnly + ":00";
//            System.out.println(dateTime);

//        String input = "2014-04-25 17:03:13";
        DateFormat inputFormat = new SimpleDateFormat("hh:mm aa");
        DateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
//        String timeOnly = outputFormat + ":00";
//        System.out.println(outputFormat);
        String timeOnly = outputFormat.format(inputFormat.parse(startTimeSelected));
//        System.out.println(timeOnly);



//        DateFormat timeFormat = new SimpleDateFormat("HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        cal.setTime(outputFormat.parse(startTimeSelected));
        cal.add(Calendar.MINUTE, 30);
//
        String endTime = outputFormat.format(cal.getTime());
        System.out.println(endTime);

        // Adds 30 min to the time to calculate for appointment end time
//        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
//        Date d = df.parse(timeOnly);
//        Calendar cal = Calendar.getInstance();
//        cal.setTime(d);
//        cal.add(Calendar.MINUTE, 30);
//        String newTime = df.format(cal.getTime());
//        String endDateTime = dateOnly + " " + newTime + ":00";
//        System.out.println(endDateTime);



        // Get Current Time
        final Date currentTime = new Date();

        final SimpleDateFormat formattedDate =
                new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

// Give it to me in GMT time.
        formattedDate.setTimeZone(TimeZone.getTimeZone("GMT"));
//        formattedDate.format(currentTime);



//        LocalDateTime dateTimeNow = LocalDateTime.now();
//        DateTimeFormatter newFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


        String currentDateTime = formattedDate.format(currentTime);

//        try {
//            Statement statement = Database.getStatement();
//            String insertQuery = "INSERT IGNORE INTO appointment (customerId, userId, title, description, location," +
//                    "type, url, start, end, createDate, createdBy) VALUES ('" + customerId.getText() + "', '" + userId.getText() + "', '" +
//                    title.getText() + "', '" + description.getText() + "', '" + location.getText() + "', '" +
//                    type.getText() + "', '" + url.getText() + "', '" + dateTime + "', '" + endDateTime + "', '" +
//                    currentDateTime + "', '" + docController.getUser().getUsername() + "')";
//            Boolean insertResult = statement.execute(insertQuery);
//
//            final Node previous = (Node) event.getSource();
//            final Stage stage = (Stage) previous.getScene().getWindow();
//            stage.close();
//        } catch (SQLException e) {
//            System.out.println(e);
//        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        start.setItems(startTimesList);
    }

}
