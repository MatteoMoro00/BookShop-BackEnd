package com.digitazon.bookShop.servicies.abstractions;

import com.digitazon.bookShop.entities.*;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.exceptions.BookNotFoundException;
import com.digitazon.bookShop.exceptions.UserNotFoundException;
import com.digitazon.bookShop.utils.ConfigBookSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BookShopService {
    Page<Book> findAll(Pageable pag);
    Page<Book> getAllByTitleLike(String titleLike, Pageable pag) throws BookNotFoundException;
    Page<Book> searchBookCustom(ConfigBookSearch configBookSearch) throws AlreadySavedException;
    Book insertBook(Book book);
    void deleteBook(int bookId) throws BookNotFoundException;
    Genre insertGenre(Genre genre) throws AlreadySavedException;
    Publisher insertPublisher(Publisher publisher) throws AlreadySavedException;
    User insertUser(User user) throws AlreadySavedException, UserNotFoundException;
    User insertUserToken(User user) throws AlreadySavedException;
    UserBookWishlist addBookToWishlist(UserBookWishlist userBookWishlist) throws AlreadySavedException;
    void removeBookToWishlist(int userBookWishlistId) throws BookNotFoundException;
}
