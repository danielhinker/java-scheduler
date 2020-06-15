package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import models.Appointment;
import models.Customer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class AppointmentsController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;
    }

    @FXML
    private Button closeButton;
    @FXML
    TableView<Appointment> appointmentTable;
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
    @FXML private Label count;

    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    Appointment appointmentClicked;
    int appointmentClickedIndex;

    public ObservableList<Appointment> getAppointmentList() {
        return appointmentList;
    }

    public void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Statement statement = Database.getStatement();
        String query = "SELECT * FROM appointment";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                Appointment appointment = new Appointment();

                appointment.setAppointmentId(results.getString(1));
                appointment.setCustomerId(results.getString(2));
                appointment.setUserId(results.getString(3));
                appointment.setTitle(results.getString(4));
                appointment.setDescription(results.getString(5));
                appointment.setLocation(results.getString(6));
                appointment.setContact(results.getString(7));
                appointment.setType(results.getString(8));
                appointment.setUrl(results.getString(9));
                appointment.setStart(results.getString(10));
                appointment.setEnd(results.getString(11));
                appointment.setCreateDate(results.getString(12));
                appointment.setCreatedBy(results.getString(13));
                appointment.setLastUpdate(results.getString(14));
                appointment.setLastUpdateBy(results.getString(15));
                appointmentList.add(appointment);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
