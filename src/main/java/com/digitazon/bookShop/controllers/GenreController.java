package com.digitazon.bookShop.controllers;

import com.digitazon.bookShop.entities.Genre;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.repositories.GenreRepository;
import com.digitazon.bookShop.servicies.abstractions.BookShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/genres")
@CrossOrigin(origins = "*")
public class GenreController {
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private BookShopService bookShopService;

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(genreRepository.findAll());

    }

    @PostMapping
    public ResponseEntity insert(@RequestBody Genre newGenre) {
        try {
            return ResponseEntity.ok(bookShopService.insertGenre(newGenre));
        } catch (AlreadySavedException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
