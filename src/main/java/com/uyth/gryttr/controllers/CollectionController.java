package com.uyth.gryttr.controllers;

import com.uyth.gryttr.model.Collection;
import com.uyth.gryttr.repository.CollectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
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

}
