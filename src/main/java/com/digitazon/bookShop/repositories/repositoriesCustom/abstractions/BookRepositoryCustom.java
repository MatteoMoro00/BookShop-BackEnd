package com.digitazon.bookShop.repositories.repositoriesCustom.abstractions;

import com.digitazon.bookShop.entities.Book;
import com.digitazon.bookShop.utils.ConfigBookSearch;
import org.springframework.data.domain.Page;

public interface BookRepositoryCustom {
    Page<Book> searchBook(ConfigBookSearch configBookSearch);
}
