package com.example.library_sber.service;

import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Book;
import com.example.library_sber.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookServiceImp implements BookService{

    private final BookRepository bookRepository;

    @Override
    public Optional<Book> getBookById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> getBookByTitle(String bookTitle) {
        return bookRepository.findByBookTitle(bookTitle);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public Book updateBook(Book book) {
        Optional<Book> repoBook = bookRepository.findById(book.getBookId());
        if (repoBook.isEmpty()) {
            throw new RuntimeException("Book with this id is not found!");
        }
        book.setBookId(repoBook.get().getBookId());
        return bookRepository.save(book);
    }

    @Override
    public void deleteBook(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(()-> new RuntimeException("Abonement is not found"));
        bookRepository.delete(book);
    }

    @Override
    public Abonement getBookOwner(Long bookId) {
        return bookRepository.findById(bookId).map(Book::getOwner).orElse(null);
    }


    @Override// Освбождает книгу (этот метод вызывается, когда человек возвращает книгу в библиотеку)
    @Transactional
    public void release(Long bookId) {
        bookRepository.findById(bookId).ifPresent(
                book -> {
                    book.setOwner(null);
                    book.setTakenAt(null);
                });
    }

    @Override
    @Transactional
    public void assign(Long bookId, Abonement selectedAbonement) {
        bookRepository.findById(bookId).ifPresent(
                book -> {
                    book.setOwner(selectedAbonement);
                    book.setTakenAt(new Date()); // текущее время
                }
        );
    }
}
