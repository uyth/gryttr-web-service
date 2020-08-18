package com.uyth.gryttr.controllers;

import com.uyth.gryttr.exceptions.ResourceNotFoundException;
import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import com.uyth.gryttr.model.dto.CollectionCreationDto;
import com.uyth.gryttr.model.dto.CollectionResponseDto;
import com.uyth.gryttr.repository.CollectionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CollectionController {

    final static String GRADE = "grade";
    final static String NAME = "name";

    @Autowired
    CollectionRepository collectionRepository;

    @GetMapping("/collections")
    public List<CollectionResponseDto> getAllBoulder() {
        return collectionRepository.findAll().stream().map(this::mapCollectionToResponseDto).collect(Collectors.toList());
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<CollectionResponseDto> getCollectionById(
            @PathVariable("id") Long id,
            @RequestParam(required=false) String sort,
            @RequestParam(required=false) String mingrade,
            @RequestParam(required=false) String maxgrade
            ) throws ResourceNotFoundException {
        sanitizeParameters(sort, mingrade, maxgrade);
        Collection collection = safeGetCollectionById(id);
        collection = filterBoulders(mingrade, maxgrade, collection);
        CollectionResponseDto responseDto = mapCollectionToResponseDto(collection);
        List<BoulderResponseDto> boulders = sortBoulders(sort, responseDto.getBoulders());
        responseDto.setBoulders(boulders);
        return ResponseEntity.ok().body(responseDto);
    }

    protected void sanitizeParameters(String sort, String minGrade, String maxGrade) throws IllegalArgumentException {
        if (sort != null) {
            if (!sort.equals(GRADE) && !sort.equals(NAME)) {
                throw new IllegalArgumentException("Illegal sorting method: " + sort
                        + ".\nOnly " + GRADE + " and " + NAME + " is allowed.");
            }
        }
        if (maxGrade != null) {
            Boulder.checkGrade(maxGrade);
        }
        if (minGrade != null) {
            Boulder.checkGrade(minGrade);
        }
        if (minGrade != null && maxGrade != null) {
            if (hasEqualOrGreaterGradeValue(maxGrade, minGrade)) {
                throw new IllegalArgumentException("Minimum grade must be lower than the maximum grade.");
            }
        }
    }

    protected boolean hasEqualOrGreaterGradeValue(String grade1, String grade2) {
        return 0 >= grade1.compareTo(grade2);
    }

    private Collection safeGetCollectionById(Long id) throws ResourceNotFoundException {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection with ID '" + id.toString() + "' was not found."));
    }

    private Collection filterBoulders(String minGrade, String maxGrade, Collection collection) {
        Set<Boulder> boulders = collection.getBoulders();
        if (maxGrade == null && minGrade == null) {
            return collection;
        }
        if (minGrade != null) {
            boulders = boulders.stream()
                    .filter(boulder -> hasEqualOrGreaterGradeValue(minGrade, boulder.getGrade()))
                    .collect(Collectors.toSet());
        }
        if (maxGrade != null) {
            boulders = boulders.stream()
                    .filter(boulder -> hasEqualOrGreaterGradeValue(boulder.getGrade(), maxGrade))
                    .collect(Collectors.toSet());
        }
        collection.setBoulders(boulders);
        return collection;
    }

    protected CollectionResponseDto mapCollectionToResponseDto(Collection collection) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(collection, CollectionResponseDto.class);
    }

    protected List<BoulderResponseDto> sortBoulders(String sort, List<BoulderResponseDto> boulders) {
        if (sort != null) {
            if (sort.equals(NAME)) {
                boulders.sort(Comparator.comparing(BoulderResponseDto::getName));
            } else if (sort.equals(GRADE)) {
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
