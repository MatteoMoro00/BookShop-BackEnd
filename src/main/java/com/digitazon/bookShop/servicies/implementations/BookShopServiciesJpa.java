package com.digitazon.bookShop.servicies.implementations;

import com.digitazon.bookShop.entities.*;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.exceptions.BookNotFoundException;
import com.digitazon.bookShop.exceptions.UserNotFoundException;
import com.digitazon.bookShop.repositories.*;
import com.digitazon.bookShop.servicies.abstractions.BookShopService;
import com.digitazon.bookShop.utils.ConfigBookSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookShopServiciesJpa implements BookShopService {
    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final PublisherRepository publisherRepository;
    private final UserRepository userRepository;
    private final UserBookWishlistRepository userBookWishlistRepository;

    public BookShopServiciesJpa(BookRepository bookRepository, GenreRepository genreRepository, AuthorRepository authorRepository,
                                PublisherRepository publisherRepository, UserRepository userRepository,
                                UserBookWishlistRepository userBookWishlistRepository) {
        this.bookRepository = bookRepository;
        this.genreRepository = genreRepository;
        this.authorRepository = authorRepository;
        this.publisherRepository = publisherRepository;
        this.userRepository = userRepository;
        this.userBookWishlistRepository = userBookWishlistRepository;
    }

    @Override
    public Page<Book> findAll(Pageable pag) {
        return bookRepository.findAll(pag);
    }

    @Override
    public Page<Book> getAllByTitleLike(String titleLike, Pageable pag) throws BookNotFoundException {
        Page<Book> booksFind = bookRepository.findByTitleLikeIgnoreCase("%" + titleLike + "%", pag);
        if (booksFind.isEmpty()) {
            throw new BookNotFoundException("no books found with title like " + titleLike);
        } else {
            return booksFind;
        }
    }

    @Override
    public Page<Book> searchBookCustom(ConfigBookSearch configBookSearch) throws AlreadySavedException {
        Author author;
        Genre genre;
        Publisher publisher;
        if (configBookSearch.getFirstName() != null) {
            author = authorRepository.findByFirstName(configBookSearch.getFirstName()).orElseThrow();
            configBookSearch.setAuthor(author);
        }
        if (configBookSearch.getLastName() != null) {
            author = authorRepository.findByLastName(configBookSearch.getLastName()).orElseThrow();
            configBookSearch.setAuthor(author);
        }
        if (configBookSearch.getGenreName() != null) {
            genre = genreRepository.findByName(configBookSearch.getGenreName()).orElseThrow();
            configBookSearch.setGenre(genre);
        }
        if (configBookSearch.getPublisherName() != null) {
            publisher = publisherRepository.findByName(configBookSearch.getPublisherName()).orElseThrow();
            configBookSearch.setPublisher(publisher);
        }
        return bookRepository.searchBook(configBookSearch);
    }

    @Override
    public Book insertBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(int bookId) throws BookNotFoundException {
        Optional<Book> optBook = bookRepository.findById(bookId);
        if (optBook.isPresent()) {
            bookRepository.delete(optBook.get());
        } else {
            throw new BookNotFoundException("doesn't exists contact with id " + bookId);
        }
    }

    @Override
    public Genre insertGenre(Genre genre) throws AlreadySavedException {
        Optional<Genre> savedGenre = genreRepository.findByName(genre.getGenreName());
        if (savedGenre.isPresent()) {
            throw new AlreadySavedException("book genre already saved");
        }
        return genreRepository.save(genre);
    }

    @Override
    public Publisher insertPublisher(Publisher publisher) throws AlreadySavedException {
        Optional<Publisher> savedPublisher = publisherRepository.findByName(publisher.getPublisherName());
        if (savedPublisher.isPresent()) {
            throw new AlreadySavedException("book publisher already saved");
        }
        return publisherRepository.save(publisher);
    }

    @Override
    public User insertUser(User user) throws AlreadySavedException, UserNotFoundException {
        Optional<User> savedUser = userRepository.findByUsernameAndPassword(user.getUsername(), user.getPassword());
        if (savedUser.isPresent()) {
            throw new AlreadySavedException("user already registered");
        }
        return userRepository.save(user);
    }

    @Override
    public User insertUserToken(User user) throws AlreadySavedException {
        List<User> users = userRepository.findAll();
        if (!users.contains(user)) {
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodePassword = passwordEncoder.encode(user.getPassword());
            User savedUser = new User(0, user.getUsername(), encodePassword, user.isAdmin());
            return userRepository.save(savedUser);
        } else {
            throw new AlreadySavedException(String.format("user with %s already present", user.getUsername()));
        }
    }

    @Override
    public UserBookWishlist addBookToWishlist(UserBookWishlist userBookWishlist) throws AlreadySavedException {
        List<UserBookWishlist> ubw = userBookWishlistRepository.findAll();
        if (!ubw.contains(userBookWishlist)) {
            return userBookWishlistRepository.save(userBookWishlist);
        } else {
            throw new AlreadySavedException(String.format("book with %s already present", userBookWishlist.getId()));
        }
    }

    @Override
    public void removeBookToWishlist(int userBookWishlistId) throws BookNotFoundException {
        Optional<UserBookWishlist> optUserBookWishlist = userBookWishlistRepository.findById(userBookWishlistId);
        if (optUserBookWishlist.isPresent()) {
            userBookWishlistRepository.delete(optUserBookWishlist.get());
        } else {
            throw new BookNotFoundException("doesn't exists a wishlist book with id " + userBookWishlistId);
        }
    }
}
