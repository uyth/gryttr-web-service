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

@RestController
@RequestMapping("/api")
public class BoulderController {

    @Autowired
    private BoulderRepository boulderRepository;

    @Autowired
    private CollectionRepository collectionRepository;

    @GetMapping("/boulders")
    public List<Boulder> getAllBoulder() {
        return boulderRepository.findAll();
    }

    @GetMapping("/boulders/{id}")
    public ResponseEntity<BoulderResponseDto> getBoulderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Boulder boulder = boulderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boulder with ID '" + id.toString() + "' was not found."));
        BoulderResponseDto boulderResponseDto = mapBoulderToBoulderDto(boulder);
        return ResponseEntity.ok().body(boulderResponseDto);
    }

    protected BoulderResponseDto mapBoulderToBoulderDto(Boulder boulder) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(boulder, BoulderResponseDto.class);
    }

    @PostMapping("/boulders")
    public ResponseEntity<BoulderResponseDto> newBoulder(@RequestBody BoulderCreationDto boulderDto) throws ResourceNotFoundException {
        Collection collection = safeGetCollectionById(boulderDto.getCollections_id());
        Boulder newBoulder = generateBoulderFromDto(boulderDto);
        addBoulderToCollection(collection, newBoulder);

        boulderRepository.save(newBoulder);
        collectionRepository.save(collection);
        BoulderResponseDto boulderResponseDto = mapBoulderToBoulderDto(newBoulder);
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

    protected Collection addBoulderToCollection(Collection collection, Boulder newBoulder) {
        newBoulder.setCollection(collection);
        List<Boulder> boulders = collection.getBoulders();
        boulders.add(newBoulder);
        return collection;
    }

}
