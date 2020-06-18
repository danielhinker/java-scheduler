package controllers;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointment;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ResourceBundle;

public class ConsultantAppointmentsController implements Initializable {

    private MenuController docController;
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();
    private ObservableList<String> consultantList = FXCollections.observableArrayList();

    @FXML
    TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> id;
    @FXML private TableColumn<Appointment, String> customerId;
    @FXML private TableColumn<Appointment, String> userId;
    @FXML private TableColumn<Appointment, String> title;
    @FXML private TableColumn<Appointment, String> description;
    @FXML private TableColumn<Appointment, String> location;
    @FXML private TableColumn<Appointment, String> contact;
    @FXML private TableColumn<Appointment, String> type;
    @FXML private TableColumn<Appointment, String> url;
    @FXML private TableColumn<Appointment, String> start;
    @FXML private TableColumn<Appointment, String> end;
    @FXML private ComboBox consultant;

    void setDocController(MenuController docController) {
        this.docController = docController;

        String query = "SELECT * FROM appointment";
        Statement statement = Database.getStatement();
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                appointmentList.add(Appointment.setAppointment(results));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        id.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        userId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        location.setCellValueFactory(new PropertyValueFactory<>("location"));
        contact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        url.setCellValueFactory(new PropertyValueFactory<>("type"));
        start.setCellValueFactory(new PropertyValueFactory<>("start"));
        end.setCellValueFactory(new PropertyValueFactory<>("end"));

        appointmentTable.setItems(appointmentList);
        appointmentTable.setOnMouseClicked((EventHandler<Event>) e -> {
        });

        query = "SELECT * from user";

        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                consultantList.add(results.getString("userName"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables);
        }
        consultant.setItems(consultantList);
    }

    @FXML
    private Button closeButton;

    public void handleApply() {
        appointmentList.clear();
        String userName = consultantList.get(consultant.getSelectionModel().getSelectedIndex());
        System.out.println(userName);
        String query = "SELECT * from appointment LEFT JOIN user ON appointment.userId = user.userId WHERE user.userName = '" + userName + "'";
        Statement statement = Database.getStatement();
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                appointmentList.add(Appointment.setAppointment(results));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables);
        }
    }

    public void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
