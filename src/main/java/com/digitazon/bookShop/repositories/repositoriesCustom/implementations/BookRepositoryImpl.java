package com.digitazon.bookShop.repositories.repositoriesCustom.implementations;

import com.digitazon.bookShop.entities.Book;
import com.digitazon.bookShop.repositories.repositoriesCustom.abstractions.BookRepositoryCustom;
import com.digitazon.bookShop.utils.ConfigBookSearch;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class BookRepositoryImpl implements BookRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Page<Book> searchBook(ConfigBookSearch configBookSearch) {
        String stringQuery = "select b from Book b ";
        boolean hasWhere = false;

        if (configBookSearch.getTitle() != null) {
            stringQuery += "where lower(b.title) like lower(concat('%', :title, '%')) ";
            hasWhere = true;
        }
        if (configBookSearch.getFirstName() != null ) {
            stringQuery += whereOrAnd(hasWhere) + " b.author.firstName = :firstName ";
            hasWhere = true;
        }
        if (configBookSearch.getLastName() != null ) {
            stringQuery += whereOrAnd(hasWhere) + " b.author.lastName = :lastName ";
            hasWhere = true;
        }
        if (configBookSearch.getGenreName() != null) {
            stringQuery += whereOrAnd(hasWhere) + " b.genre.genreName = :genreName ";
            hasWhere = true;
        }
        if (configBookSearch.getPublisherName() != null) {
            stringQuery += whereOrAnd(hasWhere) + " b.publisher.publisherName = :publisherName ";
            hasWhere = true;
        }
        if (configBookSearch.getPublicationDate() != null) {
            stringQuery += whereOrAnd(hasWhere) + " b.publication_date = :publicationDate ";
            hasWhere = true;
        }
        if (configBookSearch.getPrice() != null) {
            stringQuery += whereOrAnd(hasWhere) + " b.price <= :price ";
            hasWhere = true;
        }
        if (configBookSearch.getIsbn() != null) {
            stringQuery += whereOrAnd(hasWhere) + " b.isbn = :isbn ";
            hasWhere = true;
        }

        Query query = entityManager.createQuery(stringQuery);

        if (configBookSearch.getTitle() != null) {
            query.setParameter("title", configBookSearch.getTitle());
        }
        if (configBookSearch.getFirstName() != null) {
            query.setParameter("firstName", configBookSearch.getFirstName());
        }
        if (configBookSearch.getLastName() != null) {
            query.setParameter("lastName", configBookSearch.getLastName());
        }
        if (configBookSearch.getGenreName() != null) {
            query.setParameter("genreName", configBookSearch.getGenreName());
        }
        if (configBookSearch.getPublisherName() != null) {
            query.setParameter("publisherName", configBookSearch.getPublisherName());
        }
        if (configBookSearch.getPublicationDate() != null) {
            query.setParameter("publicationDate", configBookSearch.getPublicationDate());
        }
        if (configBookSearch.getPrice() != null) {
            query.setParameter("price", configBookSearch.getPrice());
        }
        if (configBookSearch.getIsbn() != null) {
            query.setParameter("isbn", configBookSearch.getIsbn());
        }

        if (configBookSearch.getPageSize() != 0) {
            query.setFirstResult((configBookSearch.getPageNum() /*- 1*/ ) * configBookSearch.getPageSize());
            query.setMaxResults(configBookSearch.getPageSize());
        }

        List<Book> books = query.getResultList();

        int defaultPageSize = books.size() > 0 ? books.size() : 1;

        Page<Book> page = new PageImpl<>(
                books, PageRequest.of(configBookSearch.getPageNum(),
                configBookSearch.getPageSize() == 0 ? defaultPageSize : configBookSearch.getPageSize()),
                books.size());

        /*List<Book> pageBooks = new ArrayList<Book>();
        if(page != null && page.hasContent()) {
            pageBooks = page.getContent();
        }

        return pageBooks;*/

        return page;
    }

    public String whereOrAnd(boolean hasWhere) {
        return hasWhere ? " and " : " where ";
    }
}
