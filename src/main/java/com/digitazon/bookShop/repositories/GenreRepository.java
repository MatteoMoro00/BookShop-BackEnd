package com.digitazon.bookShop.repositories;

import com.digitazon.bookShop.entities.Book;
import com.digitazon.bookShop.entities.Genre;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface GenreRepository extends JpaRepository<Genre, Integer> {
    @Query("select g from Genre g where g.genreName = :name")
    Optional<Genre> findByName(String name) throws AlreadySavedException;
}
