package com.uyth.gryttr.controllers;

import com.uyth.gryttr.model.Boulder;
import com.uyth.gryttr.repository.BoulderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
