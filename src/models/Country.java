package models;

public class Country {
    private String countryId;
    private String country;

    public Country(String countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public Country() {}

    public void setCountryId(String countryId) { this.countryId = countryId; }
    public void setCountry(String country) { this.country = country; }

    public String getCountryId() { return countryId; }
    public String getCountry() { return country; }

//    INSERT INTO country (country) VALUES ('USA');
}
