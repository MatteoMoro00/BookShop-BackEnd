package com.digitazon.bookShop.controllers;

import com.digitazon.bookShop.entities.Publisher;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.repositories.PublisherRepository;
import com.digitazon.bookShop.servicies.abstractions.BookShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/publishers")
@CrossOrigin(origins = "*")
public class PublisherController {
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private BookShopService bookShopService;

    @GetMapping
    public ResponseEntity<List<Publisher>> getAll() {
        return ResponseEntity.ok(publisherRepository.findAll());
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Publisher newPublisher) {
        try {
            return ResponseEntity.ok(bookShopService.insertPublisher(newPublisher));
        } catch (AlreadySavedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
