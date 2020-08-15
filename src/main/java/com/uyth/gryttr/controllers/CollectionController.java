package com.uyth.gryttr.controllers;

import com.uyth.gryttr.exceptions.ResourceNotFoundException;
import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CollectionController {

    @Autowired
    CollectionRepository collectionRepository;

    @GetMapping("/collections")
    public List<Collection> getAllBoulder() {
        return collectionRepository.findAll();
    }

    @GetMapping("/collections/{id}")
    public ResponseEntity<Collection> getCollectionById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        Collection collection = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection with ID '" + id.toString() + "' was not found."));
        return ResponseEntity.ok().body(collection);
    }

}
