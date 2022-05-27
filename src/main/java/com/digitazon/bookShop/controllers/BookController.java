package com.digitazon.bookShop.controllers;

import com.digitazon.bookShop.dtos.ConfigBookSearchDto;
import com.digitazon.bookShop.entities.Author;
import com.digitazon.bookShop.entities.Book;
import com.digitazon.bookShop.entities.Genre;
import com.digitazon.bookShop.entities.Publisher;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.exceptions.BookNotFoundException;
import com.digitazon.bookShop.repositories.AuthorRepository;
import com.digitazon.bookShop.repositories.BookRepository;
import com.digitazon.bookShop.repositories.GenreRepository;
import com.digitazon.bookShop.repositories.PublisherRepository;
import com.digitazon.bookShop.servicies.abstractions.BookShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@RestController
@RequestMapping("/books")
@CrossOrigin(origins = "*")
public class BookController {
    @Autowired
    private BookShopService bookShopService;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private GenreRepository genreRepository;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private PublisherRepository publisherRepository;

    @GetMapping
    public ResponseEntity getAll(@RequestParam(required = false) String titleLike, Pageable pag) {
       if (titleLike == null || titleLike.equals("")) {
            return ResponseEntity.ok(bookShopService.findAll(pag));
        } else {
            try {
                return ResponseEntity.ok(bookShopService.getAllByTitleLike(titleLike, pag));
            } catch (BookNotFoundException exception) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
            }
        }
    }

    @GetMapping("/author")
    public ResponseEntity getAllByAuthor(@RequestParam int authorId, Pageable pag) {
        try {
            return ResponseEntity.ok(bookRepository.findByAuthorId(authorId, pag));
        } catch (BookNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/genre")
    public ResponseEntity getAllByGenre(@RequestParam int genreId, Pageable pag) {
        try {
            return ResponseEntity.ok(bookRepository.findByGenreId(genreId, pag));
        } catch (BookNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/new")
    public ResponseEntity getNewestByDate() {
        try {
            return ResponseEntity.ok(bookRepository.findFirstByOrderByPublicationDateDesc().orElseThrow());
        } catch (BookNotFoundException exception) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
        }
    }

    @GetMapping("/search")
    public ResponseEntity search(ConfigBookSearchDto configBookSearchDto) {
        var config = configBookSearchDto.toConfigBookSearch();
        Page<Book> books;
        try {
            books = bookShopService.searchBookCustom(config);
            /*var response = StreamSupport.stream(books.spliterator(), false)
                    .map(BookDto::fromBook)
                    .collect(Collectors.toList());*/
            var response = StreamSupport.stream(books.spliterator(), false).collect(Collectors.toList());
            return ResponseEntity.ok(response);
        } catch (AlreadySavedException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public Book read(@PathVariable int id) {
        return bookRepository.findById(id).orElseThrow();
    }

    @PostMapping("/admin")
    public ResponseEntity insert(@RequestBody Book newBook) {
        Book savedBook = bookShopService.insertBook(newBook);
        if (savedBook.getGenre() != null) {
            Genre genre = genreRepository.findById(savedBook.getGenre().getId()).orElseThrow();
            savedBook.setGenre(genre);
        }
        if (savedBook.getAuthor() != null) {
            Author author = authorRepository.findById(savedBook.getAuthor().getId()).orElseThrow();
            savedBook.setAuthor(author);
        }
        if (savedBook.getPublisher() != null) {
            Publisher publisher = publisherRepository.findById(savedBook.getPublisher().getId()).orElseThrow();
            savedBook.setPublisher(publisher);
        }
        return ResponseEntity.ok(savedBook);
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity delete(@PathVariable int id) {
        try {
            bookShopService.deleteBook(id);
            return ResponseEntity.ok("ok");
        } catch (BookNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity update(@RequestBody Book updatedBook) {
        Book book = bookRepository.findById(updatedBook.getId()).orElseThrow();
        book.setTitle(updatedBook.getTitle());
        book.setPrice(updatedBook.getPrice());
        book.setPublicationDate(updatedBook.getPublicationDate());
        book.setImage(updatedBook.getImage());
        book.setIsbn(updatedBook.getIsbn());
        book.setDescription(updatedBook.getDescription());

        Author author = authorRepository.findById(updatedBook.getAuthor().getId()).orElseThrow();
        book.setAuthor(author);
        Genre genre = genreRepository.findById(updatedBook.getGenre().getId()).orElseThrow();
        book.setGenre(genre);
        Publisher publisher = publisherRepository.findById(updatedBook.getPublisher().getId()).orElseThrow();
        book.setPublisher(publisher);

        return ResponseEntity.ok(bookShopService.insertBook(book));
    }
}
