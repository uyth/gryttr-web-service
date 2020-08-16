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

    public String getGrade() {
        return grade;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public long getCollections_id() {
        return collections_id;
    }
}
