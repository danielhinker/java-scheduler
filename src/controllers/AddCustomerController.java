package controllers;

import com.mysql.cj.protocol.Resultset;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.stage.Stage;
import javafx.scene.control.*;
import models.*;

import java.text.SimpleDateFormat;
import java.time.*;
import java.net.URL;
import java.sql.*;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

public class AddCustomerController implements Initializable {

    private MenuController docController;

    void setDocController(MenuController docController) {
        this.docController = docController;

        Statement statement = Database.getStatement();
        String query = "SELECT AUTO_INCREMENT\n" +
                "FROM information_schema.TABLES\n" +
                "WHERE TABLE_SCHEMA = \"U077EG\"\n" +
                "AND TABLE_NAME = \"customer\";";
        ResultSet result;
        try {
            result = statement.executeQuery(query);
            result.next();
            id.setText(result.getString(1));
            id.setDisable(true);
        } catch (SQLException e) {
            System.out.println(e);
        }
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

    @FXML
    public void handleSave(ActionEvent event) {
        try {

            // Get Current Time
//            final java.util.Date currentTime = new Date();
//            final SimpleDateFormat formattedDate =
//                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            formattedDate.setTimeZone(TimeZone.getTimeZone("GMT"));
            String currentDateTime = Utilities.getCurrentDateTime();

            String userName = docController.getUser().getUsername();

            // Insert Country
            Statement statement = Database.getStatement();
            String insertQuery = "INSERT IGNORE INTO country (country, createDate, createdBy) VALUES ('" + country.getText() + "', '"
            + currentDateTime + "', '" + userName + "')";
            int insertResults = statement.executeUpdate(insertQuery);

            // Select Country
            String searchQuery = "SELECT * FROM country WHERE (country = '" + country.getText() + "')";
            ResultSet result = statement.executeQuery(searchQuery);

            // Set Country
            Country country = new Country();
            result.next();
            country.setCountryId(result.getString(1));
            country.setCountry(result.getString(2));

            // Insert City
            insertQuery = "INSERT IGNORE INTO city (city, countryId, createDate, createdBy) VALUES ('" +
                    city.getText() + "', '" + country.getCountryId() + "', '"
                    + currentDateTime + "', '" + userName + "')";
            insertResults = statement.executeUpdate(insertQuery);

            // Select City
            searchQuery = "SELECT * FROM city WHERE (countryId = '" + country.getCountryId() + "')";
            result = statement.executeQuery(searchQuery);

            // Set City
            City city = new City();
            result.next();
            city.setCityId(result.getString(1));
            city.setCity(result.getString(2));

            // Insert Address
            insertQuery = "INSERT IGNORE INTO address (address, address2, cityId, postalCode, phone, createDate, createdBy) VALUES ('"
                    + address.getText() + "', '" + address2.getText() + "', '" + city.getCityId() + "', '"
                    + postal.getText() + "', '" + phone.getText() + "', '"
                    + currentDateTime + "', '" + userName + "')";
            insertResults = statement.executeUpdate(insertQuery);

            // Select Address
            searchQuery = "SELECT * FROM address WHERE (address = '" + address.getText() + "')";
            result = statement.executeQuery(searchQuery);

            // Set Address
            Address address = new Address();
            result.next();
            address.setAddressId(result.getString(1));
            address.setAddress(result.getString(2));
            address.setAddress2(result.getString(3));
            address.setCityId(result.getString(4));
            address.setPostalCode(result.getString(5));

            // Insert Customer
            insertQuery = "INSERT IGNORE INTO customer (customerName, addressId, active, createDate, createdBy) VALUES ('"
                    + name.getText() + "', '" + address.getAddressId() + "', '1', '" + currentDateTime + "', '" + userName + "' )";
            insertResults = statement.executeUpdate(insertQuery);

            // Select Customer
            searchQuery = "SELECT * FROM customer WHERE (customerName = '" + name.getText() + "')";
            result = statement.executeQuery(searchQuery);


            docController.getCustomerList().add(Customer.setCustomer(result));

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
