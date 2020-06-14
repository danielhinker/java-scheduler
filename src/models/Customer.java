package models;

public class Customer {
    private String customerId;
    private String customerName;
    private String addressId;
    private String active;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    public Customer(String customerId, String customerName, String addressId, String active,
                    String createDate, String createdBy, String lastUpdate, String lastUpdateBy) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Customer() {}

    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    public void setAddressId(String addressId) { this.addressId = addressId; }
    public void setActive(String active) { this.active = active; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setLastUpdateBy(String lastUpdateBy) { this.lastUpdateBy = lastUpdateBy; }

    public String getCustomerId() { return customerId; }
    public String getCustomerName() { return customerName; }
    public String getAddressId() { return addressId; }
    public String getActive() { return active; }
    public String getCreateDate() { return createDate; }
    public String getCreatedBy() { return createdBy; }
    public String getLastUpdate() { return lastUpdate; }
    public String getLastUpdateBy() { return lastUpdateBy; }

    // INSERT INTO customer (customerName, addressId, active, ) VALUES ('name', 1, '1');

}
