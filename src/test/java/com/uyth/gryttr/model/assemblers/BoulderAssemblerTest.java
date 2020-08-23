package com.uyth.gryttr.model.assemblers;

import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.dto.BoulderCreationDto;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BoulderAssemblerTest {

    Long ID = 22L;
    String GRADE = "6A+";
    String NAME = "Test boulder";
    double LATITUDE = 2.0;
    double LONGITUDE = 14.0;
    Long COLLECTIONS_ID = 2L;

    @Autowired
    BoulderAssembler boulderAssembler;

    @Test
    public void testMapToDto() {
        Boulder boulder = generateBoulder();

        BoulderResponseDto boulderDto = boulderAssembler.mapToDto(boulder);

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
        collection.setBoulders(new HashSet<>(Arrays.asList(boulder)));
        return boulder;
    }

    @Test
    public void testMapToBoulder() {
        BoulderCreationDto boulderDto = generateBoulderDto();
        Boulder boulder = boulderAssembler.mapToBoulder(boulderDto);

        assertEquals(boulder.getName(), boulderDto.getName());
        assertEquals(boulder.getGrade(), boulderDto.getGrade());
        assertEquals(boulder.getLatitude(), boulderDto.getLatitude());
        assertEquals(boulder.getLongitude(), boulderDto.getLongitude());
    }

    protected BoulderCreationDto generateBoulderDto() {
        BoulderCreationDto boulderDto = new BoulderCreationDto();
        boulderDto.setName(NAME);
        boulderDto.setGrade(GRADE);
        boulderDto.setLatitude(LATITUDE);
        boulderDto.setLongitude(LONGITUDE);
        return boulderDto;
    }
}
