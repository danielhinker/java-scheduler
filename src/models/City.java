package models;

public class City {
    private String cityId;
    private String city;
    private String countryId;

    public City(String cityId, String city, String countryId) {
        this.cityId = cityId;
        this.city = city;
        this.countryId = countryId;
    }

    public City() {}

    public void setCityId(String cityId) { this.cityId = cityId; }
    public void setCity(String city) { this.city = city; }
    public void setCountryId(String countryId) { this.countryId = countryId; }

    public String getCityId() { return cityId; }
    public String getCity() { return city; }
    public String getCountryId() { return countryId; }

//    INSERT INTO city (city, countryId) VALUES ('LA', 1);

}
