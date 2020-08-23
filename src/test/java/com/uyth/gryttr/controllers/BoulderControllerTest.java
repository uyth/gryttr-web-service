package com.uyth.gryttr.controllers;

import com.uyth.gryttr.model.dto.BoulderResponseDto;
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

    @MockBean
    private BoulderController boulderController;

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

}
