package com.digitazon.bookShop.repositories;

import com.digitazon.bookShop.entities.Author;
import com.digitazon.bookShop.entities.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends JpaRepository<Author, Integer> {
    Optional<Author> findByFirstName(String firstName);
    Optional<Author> findByLastName(String lastName);
}
