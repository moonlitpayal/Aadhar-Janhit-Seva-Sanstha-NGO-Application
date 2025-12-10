package com.example.payalngo;

public class VolunteerData {
    public String name, phone, email, skills, interest;

    // Required empty constructor for Firebase
    public VolunteerData() {
    }

    // Constructor to initialize all fields
    public VolunteerData(String name, String phone, String email, String skills, String interest) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.skills = skills;
        this.interest = interest;
    }
}
