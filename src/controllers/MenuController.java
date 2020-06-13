package controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

public class MenuController {

    private LoginController docController;

    void setDocController(LoginController docController) {
        this.docController = docController;
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
