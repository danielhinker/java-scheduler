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
import models.User;
import models.Utilities;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.Optional;
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
    @FXML private TableColumn<Appointment, String> appointmentDescription;
    @FXML private TableColumn<Appointment, String> appointmentLocation;
    @FXML private TableColumn<Appointment, String> appointmentContact;
    @FXML private TableColumn<Appointment, String> appointmentType;
    @FXML private TableColumn<Appointment, String> appointmentStart;
    @FXML private TableColumn<Appointment, String> appointmentEnd;

    private User user;
    public User getUser() { return user; }

    void setDocController(LoginController docController) {
        this.docController = docController;
        this.user = docController.getUser();
    }

    private ObservableList<Customer> customerList = FXCollections.observableArrayList();
    private ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

    public ObservableList<Customer> getCustomerList() { return customerList; }
    public ObservableList<Appointment> getAppointmentList() { return appointmentList; }

    Customer customerClicked;
    int customerClickedIndex;
    Appointment appointmentClicked;
    int appointmentClickedIndex;

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Statement statement = Database.getStatement();
        String query = "SELECT * FROM customer";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                customerList.add(Customer.setCustomer(results));
            }

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        query = "SELECT * FROM appointment";
        try {
            ResultSet results = statement.executeQuery(query);
            while (results.next()) {
                appointmentList.add(Appointment.setAppointment(results));
            }
        } catch (SQLException | ParseException throwables) {
            throwables.printStackTrace();
        }

        customerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        customerName.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        customerAddressId.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        customerCreated.setCellValueFactory(new PropertyValueFactory<>("createDate"));
        customerCreatedBy.setCellValueFactory(new PropertyValueFactory<>("createdBy"));
        customerLastUpdate.setCellValueFactory(new PropertyValueFactory<>("lastUpdate"));
        customerLastUpdatedBy.setCellValueFactory(new PropertyValueFactory<>("lastUpdateBy"));
        customerTable.setItems(customerList);

        // Lambda expression is used in order to set variables when the Customer Table is clicked
        customerTable.setOnMouseClicked((EventHandler<Event>) e -> {
            appointmentClicked = null;
            appointmentClickedIndex = 0;
            customerClicked = customerTable.getSelectionModel().getSelectedItem();
            customerClickedIndex = customerTable.getSelectionModel().getSelectedIndex();
        });

        appointmentId.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
        appointmentCustomerId.setCellValueFactory(new PropertyValueFactory<>("customerId"));
        appointmentUserId.setCellValueFactory(new PropertyValueFactory<>("userId"));
        appointmentTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        appointmentDescription.setCellValueFactory(new PropertyValueFactory<>("description"));
        appointmentLocation.setCellValueFactory(new PropertyValueFactory<>("location"));
        appointmentContact.setCellValueFactory(new PropertyValueFactory<>("contact"));
        appointmentType.setCellValueFactory(new PropertyValueFactory<>("type"));
        appointmentStart.setCellValueFactory(new PropertyValueFactory<>("start"));
        appointmentEnd.setCellValueFactory(new PropertyValueFactory<>("end"));
        appointmentTable.setItems(appointmentList);

        // Lambda expression is used in order to set variables when the Appointment Table is clicked
        appointmentTable.setOnMouseClicked((EventHandler<Event>) e -> {
                customerClicked = null;
                customerClickedIndex = 0;
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
        if (customerClicked != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("Are you sure you want to delete the selected customer?");
            Optional<ButtonType> alertButton = alert.showAndWait();
            if (alertButton.get() == ButtonType.OK) {
                try {
                    Statement statement = Database.getStatement();
                    String appointmentDeleteQuery = "DELETE FROM appointment WHERE customerId = '" + customerClicked.getCustomerId() + "'";
                    String customerQuery = "DELETE FROM customer WHERE customerId = '" + customerClicked.getCustomerId() + "'";


                    statement.executeUpdate(appointmentDeleteQuery);
                    statement.executeUpdate(customerQuery);
                    appointmentList.clear();
                    String query = "SELECT * FROM appointment";
                    try {
                        ResultSet results = statement.executeQuery(query);
                        while (results.next()) {
                            appointmentList.add(Appointment.setAppointment(results));
                        }
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    customerList.remove(customerClicked);

                } catch (SQLException e) {
                    System.out.println(e);
                }

            }
            customerClicked = null;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a customer to delete.");
            alert.show();
        }
    }

    public void handleAddAppointment() {
        if (customerClicked != null) {
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
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please select a customer for the appointment.");
            alert.show();
        }
    }

    public void handleUpdateAppointment() {
        if (appointmentClicked != null) {
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
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an appointment to update.");
            alert.show();
        }
    }

    public void handleDeleteAppointment() {
        if (appointmentClicked != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Warning!");
            alert.setHeaderText("Are you sure you want to delete the selected appointment?");
            Optional<ButtonType> alertButton = alert.showAndWait();
            if (alertButton.get() == ButtonType.OK) {
                try {
                    Statement statement = Database.getStatement();
                    String searchQuery = "DELETE FROM appointment WHERE (appointmentId = '" + appointmentClicked.getAppointmentId() + "')";
                    int result = statement.executeUpdate(searchQuery);
                    appointmentList.remove(appointmentClicked);
                } catch (SQLException e) {
                }

            }
            appointmentClicked = null;
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Please select an appointment to delete");
            alert.show();
        }
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

    public void handleActivityReport() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/ActivityReport.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            loader.<ActivityReportController>getController().setDocController(this);
            stage.show();

        } catch (Exception e) {
            System.out.println((e));
        }
    }

}
