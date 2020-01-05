package DTO;

import java.time.LocalDate;

public class AdvertisementDTO {
    private int id = -1;
    private String header;
    private String body;
    private String category;
    private String phoneNumber;
    private LocalDate date;
    private int authorID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getAuthorID() {
        return authorID;
    }

    public void setAuthorID(int authorID) {
        this.authorID = authorID;
    }

    @Override
    public String toString() {
        return "AdvertisementDTO{" +
                "id=" + id +
                ", header='" + header + '\'' +
                ", body='" + body + '\'' +
                ", category='" + category + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", date=" + date +
                ", authorID=" + authorID +
                '}';
    }
    public AdvertisementDTO () {}

    public AdvertisementDTO (int id, String header, String body, String category,
                             String phoneNumber, LocalDate date, int authorID) {
        this.id = id;
        this.header = header;
        this.body = body;
        this.category = category;
        this.phoneNumber = phoneNumber;
        this.date = date;
        this.authorID = authorID;
    }
}
