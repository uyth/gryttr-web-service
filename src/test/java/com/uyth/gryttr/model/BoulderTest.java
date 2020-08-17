package com.uyth.gryttr.model;

import com.uyth.gryttr.model.dto.BoulderResponseDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class BoulderTest {

    Long ID = 22L;
    String GRADE = "5A+";
    String NAME = "Test boulder";
    double LATITUDE = 2.0;
    double LONGITUDE = 14.0;
    Long COLLECTIONS_ID = 2L;

    @Test
    public void testSetId() {
        Boulder boulder = new Boulder();
        boulder.setId(ID);
        assertEquals(ID, boulder.getId());
    }

    @Test
    public void testSetGrade() {
        Boulder boulder = new Boulder();
        boulder.setGrade(GRADE);
        assertEquals(GRADE, boulder.getGrade());
    }

    @Test
    public void testIllegalGrade() {
        Boulder boulder = new Boulder();
        assertThrows(IllegalStateException.class, () -> boulder.setGrade("A7"));
        assertThrows(IllegalStateException.class, () -> boulder.setGrade("2A"));
        assertThrows(IllegalStateException.class, () -> boulder.setGrade("6D"));
        assertThrows(IllegalStateException.class, () -> boulder.setGrade("6+"));
        boulder.setGrade("3");
        boulder.setGrade("3+");
        boulder.setGrade("6A");
        boulder.setGrade("6A+");
    }

    @Test
    public void testSetName() {
        Boulder boulder = new Boulder();
        boulder.setName(NAME);
        assertEquals(NAME, boulder.getName());
    }

    @Test
    public void testSetLatLong() {
        Boulder boulder = new Boulder();
        boulder.setLatLong(LATITUDE, LONGITUDE);
        assertEquals(LATITUDE, boulder.getLatitude());
        assertEquals(LONGITUDE, boulder.getLongitude());
    }

    @Test
    public void testSetCollection() {
        Boulder boulder = new Boulder();
        Collection collection = new Collection();
        collection.setId(COLLECTIONS_ID);
        boulder.setCollection(collection);
        collection.setBoulders(Arrays.asList(boulder));

        assertSame(boulder.getCollection(), collection);
        assertEquals(boulder.getCollectionsId(), collection.getId());
    }

    @Test
    public void testMapBoulderToBoulderDto() {
        Boulder boulder = generateBoulder();
        BoulderResponseDto boulderDto = boulder.mapToResponseDto();

        assertEquals(boulder.getId(), boulderDto.getId());
        assertEquals(boulder.getName(), boulderDto.getName());
        assertEquals(boulder.getGrade(), boulderDto.getGrade());
        assertEquals(boulder.getLatitude(), boulderDto.getLatitude());
        assertEquals(boulder.getLongitude(), boulderDto.getLongitude());
        assertEquals(boulder.getCollectionsId(), boulderDto.getCollections_id());
    }

    private Boulder generateBoulder() {
        Boulder boulder = new Boulder();
        boulder.setId(ID);
        boulder.setName(NAME);
        boulder.setGrade(GRADE);
        boulder.setLatLong(LATITUDE, LONGITUDE);
        Collection collection = new Collection();
        collection.setId(COLLECTIONS_ID);
        boulder.setCollection(collection);
        collection.setBoulders(Arrays.asList(boulder));
        return boulder;
    }

}