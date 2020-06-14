package models;

public class Country {
    private String countryId;
    private String country;

    public Country(String countryId, String country) {
        this.countryId = countryId;
        this.country = country;
    }

    public Country() {}

    public void setCountryId() { this.countryId = countryId; }
    public void setCountry() { this.country = country; }

    public String getCountryId() { return countryId; }
    public String getCountry() { return country; }

//    INSERT INTO country (country) VALUES ('USA');
}
