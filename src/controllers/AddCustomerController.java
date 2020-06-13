package controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.stage.Stage;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;
    }

    @FXML
    private Button cancelButton;

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

//    @FXML
    public void handleSave() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
