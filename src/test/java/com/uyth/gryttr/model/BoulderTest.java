package com.uyth.gryttr.model;

import com.uyth.gryttr.model.dto.BoulderResponseDto;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;


public class BoulderTest {


    @Test
    public void testMapBoulderToBoulderDto() {
        Boulder mockBoulder = generateBoulder();
        BoulderResponseDto boulderDto = mockBoulder.mapToResponseDto();

        assertEquals(mockBoulder.getId(), boulderDto.getId());
        assertEquals(mockBoulder.getName(), boulderDto.getName());
        assertEquals(mockBoulder.getGrade(), boulderDto.getGrade());
        assertEquals(mockBoulder.getLatitude(), boulderDto.getLatitude());
        assertEquals(mockBoulder.getLongitude(), boulderDto.getLongitude());
        assertEquals(mockBoulder.getCollectionsId(), boulderDto.getCollections_id());
    }

    private Boulder generateBoulder() {
        Boulder boulder = new Boulder();
        boulder.setId(2L);
        boulder.setName("Boulder");
        boulder.setGrade("6A+");
        boulder.setLatLong(12.0, 6.0);
        Collection collection = new Collection();
        collection.setId(2L);
        boulder.setCollection(collection);
        collection.setBoulders(Arrays.asList(boulder));
        return boulder;
    }

}
