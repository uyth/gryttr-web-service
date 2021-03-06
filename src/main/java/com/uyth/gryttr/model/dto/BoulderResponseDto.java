package com.uyth.gryttr.model.dto;

public class BoulderResponseDto {
    private long id;
    private String name;
    private String grade;
    private double latitude;
    private double longitude;
    private long collections_id;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

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

    public void setCollections(long collectionsId) {
        this.collections_id = collectionsId;
    }

    @Override
    public String toString() {
        Object[] data = {id, name, grade, latitude, longitude, collections_id};
        return String.format("{" +
                "\"id\":%1$s," +
                "\"name\":%2$s," +
                "\"grade\":%3$s," +
                "\"latitude\":%4$s," +
                "\"longitude\":%5$s," +
                "\"collections_id\":%6$s" +
                "}"
                , data);
    }
}
