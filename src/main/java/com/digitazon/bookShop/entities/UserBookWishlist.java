package com.digitazon.bookShop.entities;

import javax.persistence.*;

@Entity
@Table(name = "users_books_wishlist")
public class UserBookWishlist {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_books_wishlist_generator")
    @SequenceGenerator(name = "users_books_wishlist_generator", sequenceName = "users_books_wishlist_id_seq",
            allocationSize = 1)
    private int id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    public UserBookWishlist() {
    }

    public UserBookWishlist(int id, User user, Book book) {
        this.id = id;
        this.user = user;
        this.book = book;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
}
