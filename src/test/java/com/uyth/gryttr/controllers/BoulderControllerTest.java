package com.uyth.gryttr.controllers;

import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.when;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;


@SpringBootTest
@AutoConfigureMockMvc
public class BoulderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoulderController boulderController;

    Long ID = 22L;
    String GRADE = "6A+";
    String NAME = "Test boulder";
    double LATITUDE = 2.0;
    double LONGITUDE = 14.0;
    Long COLLECTIONS_ID = 2L;

    @Test
    public void testGetBoulders() throws Exception {
        List<BoulderResponseDto> dtos = new ArrayList<>();
        dtos.add(new BoulderResponseDto());
        dtos.add(new BoulderResponseDto());
        when(boulderController.getAllBoulder()).thenReturn(dtos);
        String dtoJson = "[" + dtos.stream().map(BoulderResponseDto::toString).collect(Collectors.joining(",")) + "]";

        mockMvc.perform(get("/api/boulders")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string(dtoJson));
    }

    @Test
    public void testMapBoulderToBoulderDto() {
        Boulder boulder = generateBoulder();

        BoulderResponseDto boulderDto = boulderController.mapBoulderToResponseDto(boulder);

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
}
