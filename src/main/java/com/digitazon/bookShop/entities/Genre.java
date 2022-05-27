package com.digitazon.bookShop.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "genres")
public class Genre {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genres_generator")
    @SequenceGenerator(name = "genres_generator", sequenceName = "genres_id_seq",
            allocationSize = 1)
    private int id;

    @Column(name = "name")
    private String genreName;

    @OneToMany(mappedBy = "genre")
    @JsonIgnore
    private List<Book> books;

    public Genre() {

    }

    public Genre(int id, String genreName) {
        this.id = id;
        this.genreName = genreName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Override
    public String toString() {
        return "Genre{" +
                "id=" + id +
                ", genreName='" + genreName + '\'' +
                '}';
    }
}
