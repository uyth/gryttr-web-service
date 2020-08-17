package com.uyth.gryttr.controllers;

import com.uyth.gryttr.exceptions.ResourceNotFoundException;
import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.dto.BoulderCreationDto;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import com.uyth.gryttr.repository.BoulderRepository;
import com.uyth.gryttr.repository.CollectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class BoulderController {

    @Autowired
    private BoulderRepository boulderRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @GetMapping("/boulders")
    public List<BoulderResponseDto> getAllBoulder() {
        List<Boulder> boulders = boulderRepository.findAll();
        return boulders.stream().map(this::mapBoulderToResponseDto).collect(Collectors.toList());
    }

    @GetMapping("/boulders/{id}")
    public ResponseEntity<BoulderResponseDto> getBoulderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Boulder boulder = boulderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boulder with ID '" + id.toString() + "' was not found."));
        BoulderResponseDto boulderResponseDto = mapBoulderToResponseDto(boulder);
        return ResponseEntity.ok().body(boulderResponseDto);
    }

    public BoulderResponseDto mapBoulderToResponseDto(Boulder boulder) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(boulder, BoulderResponseDto.class);
    }

    @PostMapping("/boulders")
    public ResponseEntity<BoulderResponseDto> newBoulder(@RequestBody BoulderCreationDto boulderDto) throws ResourceNotFoundException {
        Collection collection = safeGetCollectionById(boulderDto.getCollections_id());
        Boulder newBoulder = generateBoulderFromDto(boulderDto);
        collection.addBoulder(newBoulder);

        boulderRepository.save(newBoulder);
        collectionRepository.save(collection);
        BoulderResponseDto boulderResponseDto = mapBoulderToResponseDto(newBoulder);
        return ResponseEntity.ok().body(boulderResponseDto);
    }

    protected Boulder generateBoulderFromDto(BoulderCreationDto boulder) {
        Boulder newBoulder = new Boulder();
        newBoulder.setName(boulder.getName());
        newBoulder.setGrade(boulder.getGrade());
        newBoulder.setLatLong(boulder.getLatitude(), boulder.getLongitude());
        return boulderRepository.save(newBoulder);
    }

    private Collection safeGetCollectionById(Long id) throws ResourceNotFoundException {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection with ID '" + id.toString() + "' was not found."));
    }

}
