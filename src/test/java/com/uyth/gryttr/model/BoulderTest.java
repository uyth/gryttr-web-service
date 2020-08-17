package com.uyth.gryttr.model;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;


public class BoulderTest {

    Long ID = 22L;
    String GRADE = "6A+";
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
    public void testSetIllegalLatLong() {
        Boulder boulder = new Boulder();
        assertThrows(IllegalStateException.class, () -> boulder.setLatLong(91.0, LONGITUDE));
        assertThrows(IllegalStateException.class, () -> boulder.setLatLong(-91.0, LONGITUDE));
        assertThrows(IllegalStateException.class, () -> boulder.setLatLong(LATITUDE, -181.0));
        assertThrows(IllegalStateException.class, () -> boulder.setLatLong(LATITUDE, 81.0));
        boulder.setLatLong(-90.0, -180.0);
        boulder.setLatLong(90.0, 80.0);
    }


    @Test
    public void testSetCollection() {
        Boulder boulder = new Boulder();
        Collection collection = new Collection();
        collection.setId(COLLECTIONS_ID);
        boulder.setCollection(collection);
        collection.setBoulders(new HashSet<>(Arrays.asList(boulder)));

        assertSame(boulder.getCollection(), collection);
        assertEquals(boulder.getCollectionsId(), collection.getId());
    }

}
