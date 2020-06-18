package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;
import sun.rmi.runtime.Log;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AppointmentsController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;
    }

    @FXML private Button closeButton;
    @FXML TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> appointmentId;
    @FXML private TableColumn<Appointment, String> customerId;
    @FXML private TableColumn<Appointment, String> userId;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, String> location;
    @FXML private TableColumn<Appointment, String> contact;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, String> start;
    @FXML private TableColumn<Appointment, String> end;
    @FXML private RadioButton all;
    @FXML private Label count;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    Appointment appointmentClicked;
    int appointmentClickedIndex;

    public ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void handleAll() {
        appointmentList.clear();
        Statement statement = Database.getStatement();
        String query = "SELECT * FROM appointment";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                appointmentList.add(Appointment.setAppointment(results));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        count.setText(Integer.toString(appointmentList.size()));
    }

    public String getCurrentMonth() {
        final java.util.Date currentTime = new Date();
        final SimpleDateFormat formattedDate =
                new SimpleDateFormat("MM");
        formattedDate.setTimeZone(TimeZone.getTimeZone("GMT"));
        String currentDateTime = formattedDate.format(currentTime);
        return currentDateTime;
    }


    public void handleWeekly() {
        appointmentList.clear();
        Statement statement = Database.getStatement();
        String query = "SELECT * FROM appointment WHERE YEARWEEK(start)=YEARWEEK(NOW());";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                appointmentList.add(Appointment.setAppointment(results));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        count.setText(Integer.toString(appointmentList.size()));
    }

    public void handleMonthly() {

        appointmentList.clear();
        Statement statement = Database.getStatement();
        String query = "SELECT * FROM appointment WHERE MONTH(start) = '" +
                getCurrentMonth() + "'";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                appointmentList.add(Appointment.setAppointment(results));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }
        count.setText(Integer.toString(appointmentList.size()));
    }

    public void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        handleAll();
        all.setSelected(true);

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        this.location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));

        appointmentTable.setItems(appointmentList);
        appointmentTable.setOnMouseClicked((EventHandler<Event>) e -> {
            appointmentClicked = appointmentTable.getSelectionModel().getSelectedItem();
            appointmentClickedIndex = appointmentTable.getSelectionModel().getSelectedIndex();
        });

        count.setText(Integer.toString(appointmentList.size()));

    }
}
