package com.library.backend;

import com.library.security.Roles;
import org.springframework.security.access.prepost.PreAuthorize;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface BookService {
    List<Book> findAllBooks();
    Optional<Book> findBookById(Long id);

    @PreAuthorize(Roles.IS_ADMIN)
    void saveBook(Book book);

    @PreAuthorize(Roles.IS_ADMIN)
    void deleteBook(Book book);

}
