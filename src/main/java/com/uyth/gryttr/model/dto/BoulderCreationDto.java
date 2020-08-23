package com.uyth.gryttr.model.dto;

public class BoulderCreationDto {

    private String name;
    private String grade;
    private double latitude;
    private double longitude;
    private long collections_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getCollections_id() {
        return collections_id;
    }

    public void setCollections_id(long collections_id) {
        this.collections_id = collections_id;
    }
}
