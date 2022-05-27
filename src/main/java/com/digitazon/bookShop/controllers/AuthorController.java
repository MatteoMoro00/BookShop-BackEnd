package com.digitazon.bookShop.controllers;

import com.digitazon.bookShop.entities.Author;
import com.digitazon.bookShop.repositories.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/authors")
@CrossOrigin(origins = "*")
public class AuthorController {
    @Autowired
    private AuthorRepository authorRepository;

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(authorRepository.findAll());

    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Author newAuthor) {
        return ResponseEntity.ok(authorRepository.save(newAuthor));
    }
}
