package com.uyth.gryttr.controllers;

import com.uyth.gryttr.exceptions.ResourceNotFoundException;
import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.model.assemblers.BoulderAssembler;
import com.uyth.gryttr.model.dto.BoulderCreationDto;
import com.uyth.gryttr.model.dto.BoulderResponseDto;
import com.uyth.gryttr.repository.BoulderRepository;
import com.uyth.gryttr.repository.CollectionRepository;
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

    @Autowired
    private BoulderAssembler boulderAssembler;

    @GetMapping("/boulders")
    public List<BoulderResponseDto> getAllBoulder() {
        List<Boulder> boulders = boulderRepository.findAll();
        return boulders.stream().map(b -> boulderAssembler.mapToDto(b)).collect(Collectors.toList());
    }

    @GetMapping("/boulders/{id}")
    public ResponseEntity<BoulderResponseDto> getBoulderById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Boulder boulder = boulderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Boulder with ID '" + id.toString() + "' was not found."));

        BoulderResponseDto boulderResponseDto = boulderAssembler.mapToDto(boulder);
        return ResponseEntity.ok().body(boulderResponseDto);
    }

    @PostMapping("/boulders")
    public ResponseEntity<BoulderResponseDto> newBoulder(@RequestBody BoulderCreationDto boulderDto) throws ResourceNotFoundException {
        Collection collection = safeGetCollectionById(boulderDto.getCollections_id());
        Boulder newBoulder = boulderAssembler.mapToBoulder(boulderDto);
        newBoulder.setCollection(collection);
        boulderRepository.save(newBoulder);

        BoulderResponseDto boulderResponseDto = boulderAssembler.mapToDto(newBoulder);
        return ResponseEntity.ok().body(boulderResponseDto);
    }

    private Collection safeGetCollectionById(Long id) throws ResourceNotFoundException {
        return collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection with ID '" + id.toString() + "' was not found."));
    }

}
