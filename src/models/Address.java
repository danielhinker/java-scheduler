package models;

public class Address {
    private String addressId;
    private String address;
    private String address2;
    private String cityId;
    private String postalCode;
    private String phone;

    public Address(String addressId, String address, String address2, String cityId, String postalCode, String phone) {
        this.addressId = addressId;
        this.address = address;
        this.address2 = address2;
        this.cityId = cityId;
        this.postalCode = postalCode;
        this.phone = phone;
    }

    public Address() {}

    public void setAddressId(String addressId) { this.addressId = addressId; }
    public void setAddress(String address) { this.address = address; }
    public void setAddress2(String address2) { this.address2 = address2; }
    public void setCityId(String cityId) { this.cityId = cityId; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddressId() { return addressId; }
    public String getAddress() { return address; }
    public String getAddress2() { return address2; }
    public String getCityId() { return cityId; }
    public String getPostalCode() { return postalCode; }
    public String getPhone() { return phone; }

}
