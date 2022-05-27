package com.digitazon.bookShop.controllers;

import com.digitazon.bookShop.entities.*;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.exceptions.BookNotFoundException;
import com.digitazon.bookShop.repositories.BookRepository;
import com.digitazon.bookShop.repositories.UserBookWishlistRepository;
import com.digitazon.bookShop.repositories.UserRepository;
import com.digitazon.bookShop.servicies.abstractions.BookShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/wishlist")
@CrossOrigin(origins = "*")
public class UserBookWishlistController {
    @Autowired
    private BookShopService bookShopService;
    @Autowired
    private UserBookWishlistRepository userBookWishlistRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public ResponseEntity getAll() {
        return ResponseEntity.ok(userBookWishlistRepository.findAll());
    }

    @GetMapping("/user")
    public ResponseEntity getAll(@RequestParam int userId) {
        return ResponseEntity.ok(userBookWishlistRepository.findAllWishlistBooks(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity getById(@PathVariable int id) {
        return ResponseEntity.ok(userBookWishlistRepository.findById(id));
    }

    @GetMapping("/find")
    public ResponseEntity getSingleById(@RequestParam int userId, int bookId) {
        return ResponseEntity.ok(userBookWishlistRepository.findSingleWishlistBook(userId, bookId).orElseThrow());
    }

    @PostMapping
    public ResponseEntity insert(@RequestBody UserBookWishlist newBook) {
        UserBookWishlist savedBook;
        try {
            savedBook = bookShopService.addBookToWishlist(newBook);

        } catch (AlreadySavedException e) {
            throw new RuntimeException(e);
        }
        if (savedBook.getUser() != null) {
            User user = userRepository.findById(savedBook.getUser().getId()).orElseThrow();
            savedBook.setUser(user);
        }
        if (savedBook.getBook() != null) {
            Book book = bookRepository.findById(savedBook.getBook().getId()).orElseThrow();
            savedBook.setBook(book);
        }
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestParam int userId, int bookId) {
        UserBookWishlist savedWishlistBook = userBookWishlistRepository.findSingleWishlistBook(userId, bookId).orElseThrow();
        try {
            bookShopService.removeBookToWishlist(savedWishlistBook.getId());
            return ResponseEntity.ok("ok");
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
