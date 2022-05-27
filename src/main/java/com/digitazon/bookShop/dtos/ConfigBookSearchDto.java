package com.digitazon.bookShop.dtos;

import com.digitazon.bookShop.utils.ConfigBookSearch;

import java.time.LocalDate;

public class ConfigBookSearchDto {
    private String title;
    private String firstName;
    private String lastName;
    private String genreName;
    private String publisherName;
    private String publicationDate;
    private Double price;
    private String isbn;


    private Integer pageNum;
    private Integer pageSize;

    public ConfigBookSearch toConfigBookSearch() {
        var cbs = new ConfigBookSearch(title, firstName, lastName, genreName, publisherName,
                publicationDate == null ? null : LocalDate.parse(publicationDate), price, isbn);
        cbs.setPageNum(pageNum != null ? pageNum : 0);
        cbs.setPageSize(pageSize != null ? pageSize : 0);
        return cbs;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
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

    public String getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(String publicationDate) {
        this.publicationDate = publicationDate;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
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
