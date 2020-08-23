package com.uyth.gryttr.model;

import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class CollectionTest {

    private static final long ID = 2L;
    private static final String NAME = "Collection";

    @Test
    public void testInitCollection() {
        Collection collection = new Collection();
    }

    @Test
    public void testSetId() {
        Collection collection = new Collection();
        collection.setId(ID);
        assertEquals(ID, collection.getId());
    }

    @Test
    public void testSetName() {
        Collection collection = new Collection();
        collection.setName(NAME);
        assertEquals(NAME, collection.getName());
    }

    @Test
    public void testSetBoulders() {
        HashSet<Boulder> boulders = new HashSet<>();
        boulders.add(mock(Boulder.class));

        Collection collection = new Collection();
        collection.setBoulders(boulders);
        assertSame(boulders, collection.getBoulders());
    }

    @Test
    public void testAddBoulder() {
        Collection collection = generateEmptyCollection();
        collection.setId(ID);
        Boulder boulder = new Boulder();
        collection.addBoulder(boulder);
        collection.addBoulder(boulder);
        assertTrue(collection.getBoulders().contains(boulder));
    }

    private Collection generateEmptyCollection() {
        Collection collection = new Collection();
        collection.setId(ID);
        collection.setBoulders(new HashSet<>());
        collection.setName(NAME);
        return collection;
    }
}
