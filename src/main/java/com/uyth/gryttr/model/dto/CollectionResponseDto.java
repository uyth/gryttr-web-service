package com.uyth.gryttr.model.dto;

import java.util.List;

public class CollectionResponseDto {
    private long id;
    private String name;

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

    public List<BoulderResponseDto> getBoulders() {
        return boulders;
    }

    public void setBoulders(List<BoulderResponseDto> boulders) {
        this.boulders = boulders;
    }

    private List<BoulderResponseDto> boulders;
}
