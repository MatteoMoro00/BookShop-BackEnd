package com.digitazon.bookShop.utils;

import com.digitazon.bookShop.entities.Author;
import com.digitazon.bookShop.entities.Genre;
import com.digitazon.bookShop.entities.Publisher;

import java.time.LocalDate;

public class ConfigBookSearch {
    private String title;
    private String firstName;
    private String lastName;
    private String genreName;
    private String publisherName;
    private LocalDate publicationDate;
    private Double price;
    private String isbn;

    private Author author;
    private Genre genre;
    private Publisher publisher;

    private Integer pageNum;
    private Integer pageSize;

    public ConfigBookSearch() {
    }

    public ConfigBookSearch(String title, String firstName, String lastName, String genreName,
                            String publisherName, LocalDate publicationDate, Double price, String isbn) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.genreName = genreName;
        this.publisherName = publisherName;
        this.publicationDate = publicationDate;
        this.price = price;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String titleLike) {
        this.title = title;
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

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    public LocalDate getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(LocalDate publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Double getPrice() {
        return price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public Publisher getPublisher() {
        return publisher;
    }

    public void setPublisher(Publisher publisher) {
        this.publisher = publisher;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
