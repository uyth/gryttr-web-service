package com.uyth.gryttr.controllers;

import com.uyth.gryttr.exceptions.ResourceNotFoundException;
import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import com.uyth.gryttr.repository.BoulderRepository;
import com.uyth.gryttr.repository.CollectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BoulderController {

    @Autowired
    private BoulderRepository boulderRepository;

    @GetMapping("/boulders")
    public List<Boulder> getAllBoulder() {
        return boulderRepository.findAll();
    }

    @GetMapping("/boulders/{id}")
    public ResponseEntity<BoulderResponseDto> getBoulderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Boulder boulder = boulderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boulder with ID '" + id.toString() + "' was not found."));
        ModelMapper modelMapper = new ModelMapper();
        BoulderResponseDto boulderResponseDto = modelMapper.map(boulder, BoulderResponseDto.class);
        return ResponseEntity.ok().body(boulderResponseDto);
    }


}
