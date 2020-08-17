package com.uyth.gryttr.model;

import com.uyth.gryttr.model.dto.BoulderResponseDto;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
@Table(name = "boulders")
@EntityListeners(AuditingEntityListener.class)
public class Boulder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "grade")
    private String grade;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @ManyToOne
    @JoinColumn(name="collections_id")
    private Collection collection;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        checkGrade(grade);
        this.grade = grade;
    }

    private void checkGrade(String grade) throws IllegalStateException {
        Pattern pattern = Pattern.compile("([3-5]|[6-9][A-C])\\+?");
        Matcher matcher = pattern.matcher(grade);
        if (!matcher.matches()) {
            throw new IllegalStateException("Grade " + grade + " is not on the correct format.");
        }
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLatLong(double latitude, double longitude) throws IllegalStateException {
        checkLatLong(latitude, longitude);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    private void checkLatLong(double latitude, double longitude) throws IllegalStateException {
        if (latitude < -90.0 || latitude > 90) {
            throw new IllegalStateException("Latitude must have a value between -90 and 90");
        }
        if (longitude < -180.0 || longitude > 80) {
            throw new IllegalStateException("Longitude must have a value between -180 and 80");
        }
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    public long getCollectionsId() {
        return this.collection.getId();
    }

    public BoulderResponseDto mapToResponseDto() {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(this, BoulderResponseDto.class);
    }

}