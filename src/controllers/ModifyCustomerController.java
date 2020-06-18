package controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.*;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class ModifyCustomerController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;

        id.setDisable(true);

        Statement statement = Database.getStatement();
        String query = "SELECT * FROM customer WHERE customerId = '" + docController.customerClicked.getCustomerId() + "'";
        ResultSet result;
        try {
            result = statement.executeQuery(query);
            result.next();
            id.setText(result.getString(1));
            name.setText(result.getString(2));

            query = "SELECT * FROM address WHERE addressId = '" + docController.customerClicked.getAddressId() + "'";
            result = statement.executeQuery(query);
            result.next();

            address.setText(result.getString(2));
            address2.setText(result.getString(3));
            postal.setText(result.getString(5));
            phone.setText(result.getString(6));

            query = "SELECT * FROM city WHERE cityId = '" + result.getString(4) + "'";
            result = statement.executeQuery(query);
            result.next();

            city.setText(result.getString(2));

            query = "SELECT * FROM country WHERE countryId = '" + result.getString(3) + "'";
            result = statement.executeQuery(query);
            result.next();

            country.setText(result.getString(2));

        } catch (SQLException e) {
            System.out.println(e);
        }
        id.setDisable(true);
    }

    @FXML private Button cancelButton;
    @FXML private TextField id;
    @FXML private TextField name;
    @FXML private TextField address;
    @FXML private TextField address2;
    @FXML private TextField country;
    @FXML private TextField city;
    @FXML private TextField postal;
    @FXML private TextField phone;

    public void handleCancel() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void handleSave(ActionEvent event) {
        try {

            // Get Current Time
            String currentDateTime = Utilities.getCurrentDateTime();

            String userName = docController.getUser().getUsername();
            Statement statement = Database.getStatement();

            // Insert Customer
            String insertQuery = "UPDATE customer SET customerName = '" + name.getText() + "', addressId = '"
                    + docController.customerClicked.getAddressId() + "', active = '1', lastUpdate = '" + currentDateTime + "', lastUpdateBy = '"
                    + userName + "' WHERE customerId = '" + docController.customerClicked.getCustomerId() + "'";
            Boolean insertResults = statement.execute(insertQuery);

            // Select Address
            String addressSelectQuery = "SELECT * FROM address WHERE (addressId = '" + docController.customerClicked.getAddressId() + "')";
            ResultSet addressResult = statement.executeQuery(addressSelectQuery);
            addressResult.next();
            String cityId = addressResult.getString(4);

            // Select City
            String citySelectQuery = "SELECT * FROM city WHERE (cityId = '" + cityId + "')";
            ResultSet cityResult = statement.executeQuery(citySelectQuery);
            cityResult.next();
            String countryId = cityResult.getString(3);

            // Insert Address
            insertQuery = "UPDATE address SET address = '" + address.getText() + "', address2 = '" + address2.getText()
                    + "', cityId = '" + cityId + "', postalCode = '" + postal.getText() + "', phone = '"
                    + phone.getText() + "', lastUpdate = '" + currentDateTime + "', lastUpdateBy = '" + userName + "' WHERE addressId = '" + docController.customerClicked.getAddressId() + "'";
            statement.execute(insertQuery);

            // Insert City
            insertQuery = "UPDATE city SET city = '" + city.getText() + "', countryId = '" + countryId + "', lastUpdate = '"
                + currentDateTime + "', lastUpdateBy = '" + userName + "' WHERE cityId = '" + cityId + "'";
            statement.execute(insertQuery);

            // Insert Country
            insertQuery = "UPDATE country SET country = '" + country.getText() + "', createDate = '"
                    + currentDateTime + "', lastUpdateBy = '" + userName + "'";
            statement.execute(insertQuery);

            // Select Customer
            String customerSelectQuery = "SELECT * FROM customer WHERE (customerId = '" + docController.customerClicked.getCustomerId() + "')";
            ResultSet CustomerResult = statement.executeQuery(customerSelectQuery);
            CustomerResult.next();

            // Set Customer
            docController.getCustomerList().set(docController.customerClickedIndex, Customer.setCustomer(CustomerResult));

        } catch (SQLException e) {
            System.out.println(e);
        }

        final Node previous = (Node) event.getSource();
        final Stage stage = (Stage) previous.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
