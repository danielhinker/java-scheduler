package models;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Appointment {
    private String appointmentId;
    private String customerId;
    private String userId;
    private String title;
    private String description;
    private String location;
    private String contact;
    private String type;
    private String url;
    private String start;
    private String end;
    private String createDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBy;

    public Appointment(String appointmentId, String customerId, String userId, String title, String description,
                       String location, String contact, String type, String url, String start, String end,
                       String createDate, String createdBy, String lastUpdate, String lastUpdateBy) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createDate = createDate;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBy = lastUpdateBy;
    }

    public Appointment() {}

    public void setAppointmentId(String appointmentId) { this.appointmentId = appointmentId; }
    public void setCustomerId(String customerId) { this.customerId = customerId; }
    public void setUserId(String userId) { this.userId = userId; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setLocation(String location) { this.location = location; }
    public void setContact(String contact) { this.contact = contact; }
    public void setType(String type) { this.type = type; }
    public void setUrl(String url) { this.url = url; }
    public void setStart(String start) { this.start = start; }
    public void setEnd(String end) { this.end = end; }
    public void setCreateDate(String createDate) { this.createDate = createDate; }
    public void setCreatedBy(String createdBy) { this.createdBy = createdBy; }
    public void setLastUpdate(String lastUpdate) { this.lastUpdate = lastUpdate; }
    public void setLastUpdateBy(String lastUpdateBy) { this.lastUpdateBy = lastUpdateBy; }

    public static Appointment setAppointment(ResultSet result) throws SQLException {
        Appointment appointment = new Appointment();
        appointment.setAppointmentId(result.getString(1));
        appointment.setCustomerId(result.getString(2));
        appointment.setUserId(result.getString(3));
        appointment.setTitle(result.getString(4));
        appointment.setDescription(result.getString(5));
        appointment.setLocation(result.getString(6));
        appointment.setContact(result.getString(7));
        appointment.setType(result.getString(8));
        appointment.setUrl(result.getString(9));
        appointment.setStart(result.getString(10));
        appointment.setEnd(result.getString(11));
        appointment.setCreateDate(result.getString(12));
        appointment.setCreatedBy(result.getString(13));
        appointment.setLastUpdate(result.getString(14));
        appointment.setLastUpdateBy(result.getString(15));
        return appointment;
    }

    public String getAppointmentId() { return appointmentId; }
    public String getCustomerId() { return customerId; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getContact() { return contact; }
    public String getType() { return type; }
    public String getUrl() { return url; }
    public String getStart() { return start; }
    public String getEnd() { return end; }
    public String getCreateDate() { return createDate; }
    public String getCreatedBy() { return createdBy; }
    public String getLastUpdate() { return lastUpdate; }
    public String getLastUpdateBy() { return lastUpdateBy; }

    // INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end) VALUES (2, 1, 'title', 'desc', 'In person', 'me', 'type', 'url', '2020-06-14T14:25:10', '2020-06-14T16:25:10');
}
