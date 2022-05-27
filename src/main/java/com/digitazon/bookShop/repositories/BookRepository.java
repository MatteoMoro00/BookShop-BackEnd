package com.digitazon.bookShop.repositories;

import com.digitazon.bookShop.entities.Book;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import com.digitazon.bookShop.exceptions.BookNotFoundException;
import com.digitazon.bookShop.repositories.repositoriesCustom.abstractions.BookRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<Book, Integer>, BookRepositoryCustom {

    Page<Book> findAll(Pageable pageable);
    @Query("select b from Book b where lower(b.title) like lower(concat('%', :titleLike, '%'))")
    Page<Book> findByTitleLikeIgnoreCase(String titleLike, Pageable pag) throws BookNotFoundException;

    @Query("select b from Book b where b.title = :title")
    Optional<Book> findByTitle(String title) throws AlreadySavedException;

    @Query("select b from Book b where b.isbn = :isbn")
    Optional<Book> findByIsbn(String isbn);

    @Query("select b from Book b where b.price <= :price")
    List<Book> findByPriceLessThan(double price);

    @Query("select b from Book b where b.author.id = :authorId")
    List<Book> findByAuthorId(int authorId, Pageable pag) throws BookNotFoundException;

    List<Book> findByGenreId(int genreId, Pageable pag) throws BookNotFoundException;

    Optional<Book> findFirstByOrderByPublicationDateDesc() throws BookNotFoundException;
}
