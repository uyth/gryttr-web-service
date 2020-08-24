package com.uyth.gryttr.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.assemblers.BoulderAssembler;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import com.uyth.gryttr.repository.BoulderRepository;
import org.junit.jupiter.api.Test;

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
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
@AutoConfigureMockMvc
public class BoulderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    ObjectMapper mapper = new ObjectMapper();

    @MockBean
    BoulderRepository repository;

    @Autowired
    BoulderAssembler assembler;

    @Test
    public void testGetBoulders() throws Exception {
        List<Boulder> boulders = new ArrayList<>();
        Collection collection = new Collection();
        collection.setId(1L);
        Boulder b1 = new Boulder();
        b1.setId(1L);
        b1.setLatLong(1, 2);
        b1.setGrade("6A+");
        b1.setCollection(collection);

        Boulder b2 = new Boulder();
        b2.setId(3L);
        b2.setLatLong(3, 5);
        b2.setGrade("7B");
        b2.setCollection(collection);

        boulders.add(b1);
        boulders.add(b2);
        when(repository.findAll()).thenReturn(boulders);

        List<BoulderResponseDto> dtos = boulders.stream().map(b -> assembler.mapToDto(b)).collect(Collectors.toList());
        String bouldersJson = mapper.writeValueAsString(dtos);

        mockMvc.perform(get("/api/boulders"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(bouldersJson));
    }

}
