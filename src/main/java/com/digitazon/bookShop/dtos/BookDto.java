package com.digitazon.bookShop.dtos;

import com.digitazon.bookShop.entities.Author;
import com.digitazon.bookShop.entities.Book;
import com.digitazon.bookShop.entities.Genre;
import com.digitazon.bookShop.entities.Publisher;

import java.time.LocalDate;

public class BookDto {
    private int id;
    private String title;
    private int authorId;
    private String firstName;
    private String lastName;
    private int genreId;
    private String genreName;
    private int publisherId;
    private String publisherName;
    private double price;
    private String isbn;
    private String image;
    private LocalDate publicationDate;
    private String description;

    public BookDto() {
    }

    public BookDto(int id, String title, int authorId, String firstName, String lastName, int genreId, String genreName, int publisherId, String publisherName, double price, String isbn, String image, LocalDate publicationDate, String description) {
        this.id = id;
        this.title = title;
        this.authorId = authorId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genreId = genreId;
        this.genreName = genreName;
        this.publisherId = publisherId;
        this.publisherName = publisherName;
        this.price = price;
        this.isbn = isbn;
        this.image = image;
        this.publicationDate = publicationDate;
        this.description = description;
    }

    public Book toBook() {
        Author author = new Author();
        author.setId(authorId);
        author.setFirstName(firstName);
        author.setLastName(lastName);
        Genre genre = new Genre();
        genre.setId(genreId);
        genre.setGenreName(genreName);
        Publisher publisher = new Publisher();
        publisher.setId(publisherId);
        publisher.setPublisherName(publisherName);
        return new Book(id, title, author, publicationDate, price, image, isbn, publisher, genre, description);
    }

    public static BookDto fromBook(Book book) {
        return new BookDto(book.getId(),
                           book.getTitle(),
                           book.getAuthor().getId(),
                           book.getAuthor().getFirstName(),
                           book.getAuthor().getLastName(),
                           book.getGenre().getId(),
                           book.getGenre().getGenreName(),
                           book.getPublisher().getId(),
                           book.getPublisher().getPublisherName(),
                           book.getPrice(),
                           book.getIsbn(),
                           book.getImage(),
                           book.getPublicationDate(),
                           book.getDescription());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public int getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(int publisherId) {
        this.publisherId = publisherId;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
