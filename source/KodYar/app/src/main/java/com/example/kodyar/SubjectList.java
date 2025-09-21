package com.example.kodyar;

public class SubjectList {
    private int id;
    private String address;
    private String subject;
    private String kod;
    private String about;

    public SubjectList(int id, String address, String subject, String kod, String about) {
        this.id = id;
        this.address = address;
        this.subject = subject;
        this.kod = kod;
        this.about = about;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getKod() {
        return kod;
    }

    public void setKod(String kod) {
        this.kod = kod;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
