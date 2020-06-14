package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import models.Appointment;
import models.Customer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    private LoginController docController;

    @FXML TableView<Customer> customerTable;
    @FXML private TableColumn<Customer, String> customerId;
    @FXML private TableColumn<Customer, String> customerName;
    @FXML private TableColumn<Customer, String> customerAddressId;
    @FXML private TableColumn<Customer, String> customerCreated;
    @FXML private TableColumn<Customer, String> customerCreatedBy;
    @FXML private TableColumn<Customer, String> customerLastUpdate;
    @FXML private TableColumn<Customer, String> customerLastUpdatedBy;

    @FXML TableView<Appointment> appointmentTable;
    @FXML private TableColumn<Appointment, String> appointmentId;
    @FXML private TableColumn<Appointment, String> appointmentCustomerId;
    @FXML private TableColumn<Appointment, String> appointmentUserId;
    @FXML private TableColumn<Appointment, String> appointmentTitle;
    @FXML private TableColumn<Appointment, String> appointmentCreatedBy;
    @FXML private TableColumn<Appointment, String> appointmentLastUpdate;
    @FXML private TableColumn<Appointment, String> appointmentLastUpdatedBy;

    void setDocController(LoginController docController) {
        this.docController = docController;
    }

    private ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
    private ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();

    Customer customerClicked;
    int customerClickedIndex;
    Appointment appointmentClicked;
    int appointmentClickedIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Statement statement = Database.queryStatement();
        String query = "SELECT * FROM customer";
        try {
            ArrayList<String[]> result = new ArrayList<String[]>();
            ResultSet results = statement.executeQuery(query);
//        System.out.println(results.);
            int columnCount = results.getMetaData().getColumnCount();
            while (results.next()) {
                String[] row = new String[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    row[i] = results.getString(i + 1);
                }
                result.add(row);
            }
//            System.out.println(result.get(0));
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressId.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        customerCreated.setCellValueFactory(new PropertyValueFactory<>("active"));
//        customerCreatedBy.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        customerLastUpdate.setCellValueFactory(new PropertyValueFactory<>("stock"));
//        customerLastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("stock"));
        customerTable.setItems(allCustomers);
        customerTable.setOnMouseClicked((EventHandler<Event>) e -> {
            customerClicked = customerTable.getSelectionModel().getSelectedItem();
            customerClickedIndex = customerTable.getSelectionModel().getSelectedIndex();
        });


        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentTable.setItems(allAppointments);
        appointmentTable.setOnMouseClicked((EventHandler<Event>) e -> {
            appointmentClicked = appointmentTable.getSelectionModel().getSelectedItem();
            appointmentClickedIndex = appointmentTable.getSelectionModel().getSelectedIndex();
        });
    }

    public void handleLogOff(ActionEvent actionEvent) {
        try {
            ((Node) (actionEvent.getSource())).getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
//            loader.<MenuController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleAddCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AddCustomer.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<AddCustomerController>getController().setDocController(this);
//            AddCustomerController controller = loader.getController();
//            controller.partTable.getItems().setAll((inventory.getAllParts()));

            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleUpdateCustomer() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ModifyCustomer.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<ModifyCustomerController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleDeleteCustomer() {

    }

    public void handleAddAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AddAppointment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<AddAppointmentController>getController().setDocController(this);
//            AddCustomerController controller = loader.getController();
//            controller.partTable.getItems().setAll((inventory.getAllParts()));

            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleUpdateAppointment() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ModifyAppointment.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<ModifyAppointmentController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleDeleteAppointment() {

    }

    public void handleAppointmentType() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/AppointmentTypes.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<AppointmentTypesController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleAllAppointments() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/Appointments.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<AppointmentsController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleConsultantsSchedule() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ConsultantAppointments.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<ConsultantAppointmentsController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

    public void handleCustomerStatus() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/CustomerStatusReport.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<CustomerStatusController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

}
