package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class AddAppointmentController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;
    }

    @FXML
    private Button cancelButton;

    @FXML
    private Button saveButton;

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }


    public void handleSave() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}
