package com.example.smartstudybuddy.data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "gpa_entries")
public class GPAEntry {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String courseName;
    private int creditHours;
    private double grade;

    public GPAEntry(String courseName, int creditHours, double grade) {
        this.courseName = courseName;
        this.creditHours = creditHours;
        this.grade = grade;
    }

    // Getters and setters
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getCourseName() {
        return courseName;
    }
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    public int getCreditHours() {
        return creditHours;
    }
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }
    public double getGrade() {
        return grade;
    }
    public void setGrade(double grade) {
        this.grade = grade;
    }
}
