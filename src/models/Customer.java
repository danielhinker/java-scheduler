package models;

public class Customer {
    private String customerId;
    private String customerName;
    private String addressId;
    private String active;

    public Customer(String customerId, String customerName, String addressId, String active) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
    }

    public Customer() {}

    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setAddressId(String addressId) { this.addressId = addressId; }
    public void setActive(String active) { this.active = active; }

    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getAddressId() { return addressId; }
    public String getActive() { return active; }

    // INSERT INTO customer (customerName, addressId, active) VALUES ('name', 1, '1');

}
