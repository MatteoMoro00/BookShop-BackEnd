package com.digitazon.bookShop.repositories;

import com.digitazon.bookShop.entities.Publisher;
import com.digitazon.bookShop.exceptions.AlreadySavedException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PublisherRepository extends JpaRepository<Publisher, Integer> {
    @Query("select p from Publisher p where p.publisherName = :name")
    Optional<Publisher> findByName(String name) throws AlreadySavedException;
}
