package com.example.library_sber.service;

import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Book;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

public interface BookService {
    Optional<Book> getBookById(Long bookId);
    List<Book> getAllBooks();
    Optional<Book> getBookByTitle(String bookTitle);
    Book addBook(Book book);
    Book updateBook(Book book);
    void deleteBook(Long bookId);
    Abonement getBookOwner(Long bookId);
    @Transactional
    void release(Long bookId);
    @Transactional
    void assign(Long bookId, Abonement selectedAbonement);
}
