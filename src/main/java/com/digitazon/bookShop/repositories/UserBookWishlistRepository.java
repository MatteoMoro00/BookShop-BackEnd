package com.digitazon.bookShop.repositories;

import com.digitazon.bookShop.entities.Book;
import com.digitazon.bookShop.entities.UserBookWishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserBookWishlistRepository extends JpaRepository<UserBookWishlist, Integer> {
    @Query("select b from Book b join UserBookWishlist ub on b.id = ub.book.id where ub.user.id = :userId")
    List<Book> findAllWishlistBooks(int userId);

    @Query("select ub from UserBookWishlist ub where ub.user.id = :userId and ub.book.id = :bookId")
    Optional<UserBookWishlist> findSingleWishlistBook(int userId, int bookId);
}
