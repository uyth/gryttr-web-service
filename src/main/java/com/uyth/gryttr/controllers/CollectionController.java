package com.uyth.gryttr.controllers;

import com.uyth.gryttr.exceptions.ResourceNotFoundException;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import com.uyth.gryttr.model.dto.CollectionCreationDto;
import com.uyth.gryttr.model.dto.CollectionResponseDto;
import com.uyth.gryttr.repository.CollectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CollectionController {

    @Autowired
    CollectionRepository collectionRepository;

    @GetMapping("/collections")
    public List<CollectionResponseDto> getAllBoulder() {
        return collectionRepository.findAll().stream().map(this::mapCollectionToResponseDto).collect(Collectors.toList());
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<CollectionResponseDto> getCollectionById(
            @PathVariable("id") Long id, @RequestParam(required=false) String sort) throws ResourceNotFoundException {
        Collection collection = safeGetCollectionById(id);
        CollectionResponseDto responseDto = mapCollectionToResponseDto(collection);
        List<BoulderResponseDto> boulders = sortBoulders(sort, responseDto.getBoulders());
        responseDto.setBoulders(boulders);
        return ResponseEntity.ok().body(responseDto);
    }

    private Collection safeGetCollectionById(Long id) throws ResourceNotFoundException {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection with ID '" + id.toString() + "' was not found."));
    }

    protected CollectionResponseDto mapCollectionToResponseDto(Collection collection) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(collection, CollectionResponseDto.class);
    }

    protected List<BoulderResponseDto> sortBoulders(String sort, List<BoulderResponseDto> boulders) {
        if (sort != null) {
            if (sort.equals("name")) {
                boulders.sort(Comparator.comparing(BoulderResponseDto::getName));
            } else if (sort.equals("grade")) {
                boulders.sort(Comparator.comparing(BoulderResponseDto::getGrade));
            }
        }
        return boulders;
    }

    @PostMapping("/collections")
    public ResponseEntity<CollectionResponseDto> addCollection(@RequestBody CollectionCreationDto collectionDto) {
        Collection collection = generateCollection(collectionDto);
        CollectionResponseDto responseDto = mapCollectionToResponseDto(collection);
        return ResponseEntity.ok().body(responseDto);
    }

    protected Collection generateCollection(CollectionCreationDto collectionDto) {
        Collection collection = new Collection();
        collection.setName(collectionDto.getName());
        collection.setBoulders(new HashSet<>());
        return collectionRepository.save(collection);
    }

}
