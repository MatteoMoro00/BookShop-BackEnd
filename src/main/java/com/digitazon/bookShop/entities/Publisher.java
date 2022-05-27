package com.digitazon.bookShop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "publishers")
public class Publisher {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "publishers_generator")
    @SequenceGenerator(name = "publishers_generator", sequenceName = "publishers_id_seq",
            allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String publisherName;

    @OneToMany(mappedBy = "publisher")
    @JsonIgnore
    private List<Book> books;

    public Publisher() {
    }

    public Publisher(int id, String publisherName) {
        this.id = id;
        this.publisherName = publisherName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublisherName() {
        return publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }
}
