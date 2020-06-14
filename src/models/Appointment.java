package models;

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

    public Appointment(String appointmentId, String customerId, String userId, String title, String description,
                       String location, String contact, String type, String url, String start, String end) {
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
    public void setStart(String start) { this.start = start; }
    public void setEnd(String end) { this.end = end; }

    public String getAppointmentId() { return appointmentId; }
    public String getCustomerId() { return customerId; }
    public String getUserId() { return userId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getLocation() { return location; }
    public String getContact() { return contact; }
    public String getType() { return type; }
    public String getStart() { return start; }
    public String getEnd() { return end; }

    // INSERT INTO appointment (customerId, userId, title, description, location, contact, type, url, start, end) VALUES (2, 1, 'title', 'desc', 'In person', 'me', 'type', 'url', '2020-06-14T14:25:10', '2020-06-14T16:25:10');
}
