package com.team10.smarthospital.model;

public class VisitRecord {
    public String date;
    public String department;
    public String diagnosis;
    public String doctor;

    public VisitRecord(String date, String department, String diagnosis, String doctor) {
        this.date = date;
        this.department = department;
        this.diagnosis = diagnosis;
        this.doctor = doctor;
    }
}
