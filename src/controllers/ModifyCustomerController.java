package controllers;

import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Address;
import models.City;
import models.Country;
import models.Customer;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.TimeZone;

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
            final java.util.Date currentTime = new Date();
            final SimpleDateFormat formattedDate =
                    new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            formattedDate.setTimeZone(TimeZone.getTimeZone("GMT"));
            String currentDateTime = formattedDate.format(currentTime);

            String userName = docController.getUser().getUsername();
            Statement statement = Database.getStatement();

            // Insert Customer
            String insertQuery = "UPDATE customer SET customerName = '" + name.getText() + "', addressId = '"
                    + docController.customerClicked.getAddressId() + "', active = '1', lastUpdate = '" + currentDateTime + "', createdBy = '"
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

            // Select Country
//            String countySelectQuery = "SELECT * FROM country WHERE (countryId = '" + country.getText() + "')";
//            ResultSet countryResult = statement.executeQuery(countySelectQuery);
//            countryResult.next();
//            String countryId = countryResult.getString(1);

            // Insert Address
            insertQuery = "UPDATE address SET address = '" + address.getText() + "', address2 = '" + address2.getText()
                    + "', cityId = '" + cityId + "', postalCode = '" + postal.getText() + "', phone = '"
                    + phone.getText() + "', lastUpdate = '" + currentDateTime + "', lastUpdateBy = '" + userName + "' WHERE addressId = '" + docController.customerClicked.getAddressId() + "'";
            insertResults = statement.execute(insertQuery);

            // Insert City
            insertQuery = "UPDATE city SET city = '" + city.getText() + "', countryId = '" + countryId + "', lastUpdate = '"
                + currentDateTime + "', lastUpdateBy = '" + userName + "' WHERE cityId = '" + cityId + "'";
            insertResults = statement.execute(insertQuery);

            // Insert Country
            String countryInsertQuery = "UPDATE country SET country = '" + country.getText() + "', createDate = '"
                    + currentDateTime + "', lastUpdateBy = '" + userName + "'";
            insertResults = statement.execute(insertQuery);


////             Set Country
//            Country country = new Country();
//            result.next();
//            country.setCountryId(result.getString(1));
//            country.setCountry(result.getString(2));
//            System.out.println(country.getCountry());

            // Set City
//            City city = new City();
//            result.next();
//            city.setCityId(result.getString(1));
//            city.setCity(result.getString(2));

            // Set Address
//            Address address = new Address();
//            result.next();
//            address.setAddressId(result.getString(1));
//            address.setAddress(result.getString(2));
//            address.setAddress2(result.getString(3));
//            address.setCityId(result.getString(4));
//            address.setPostalCode(result.getString(5));

            // Select Customer
            String customerSelectQuery = "SELECT * FROM customer WHERE (customerId = '" + docController.customerClicked.getCustomerId() + "')";
            ResultSet CustomerResult = statement.executeQuery(customerSelectQuery);
            CustomerResult.next();
//            String addressId = customerResult.getString(3);

            // Set Customer
            Customer customer = new Customer();
//            CustomerResult.next();
            customer.setCustomerId(CustomerResult.getString(1));
            customer.setCustomerName(CustomerResult.getString(2));
            customer.setAddressId(CustomerResult.getString(3));
            customer.setActive(CustomerResult.getString(4));
            customer.setCreateDate(CustomerResult.getString(5));
            customer.setCreatedBy(CustomerResult.getString(6));
            customer.setLastUpdate(CustomerResult.getString(7));
            customer.setLastUpdateBy(CustomerResult.getString(8));

            docController.getCustomerList().set(docController.customerClickedIndex, customer);

//            System.out.println(city.getCity());
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
