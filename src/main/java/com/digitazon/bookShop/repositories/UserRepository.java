package com.digitazon.bookShop.repositories;

import com.digitazon.bookShop.entities.User;
import com.digitazon.bookShop.exceptions.UserNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    @Query("select u from User u where u.username = :username and u.password = :password")
    Optional<User> findByUsernameAndPassword(String username, String password) throws UserNotFoundException;

    @Query("select u from User u where u.username = :username")
    Optional<User> findByUsername(String username);
}
