package com.example.library_sber.controller;

import com.example.library_sber.model.converter.AbonementMapper;
import com.example.library_sber.model.converter.BookMapper;
import com.example.library_sber.model.dto.AbonementRequestDTO;
import com.example.library_sber.model.dto.BookRequestDTO;
import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Book;
import com.example.library_sber.repository.AbonementRepository;
import com.example.library_sber.service.AbonementService;
import com.example.library_sber.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/librarian")
@RequiredArgsConstructor
// Добавить сваггеровские аннотации
public class BookControllerLibr {
    private final BookService bookService;
    private final AbonementRepository abonementRepository;

    @GetMapping("/books/{bookId}/owner")
    public ResponseEntity<Abonement> getBookOwner(@PathVariable Long bookId) {
        Abonement abonement = bookService.getBookOwner(bookId);
        if (abonement != null) {
            return new ResponseEntity<>(abonement, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody BookRequestDTO dto) {
        Book book = BookMapper.mapToEntity(dto);
        book.setQuantity(dto.getQuantity());
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<BookRequestDTO> updateBook(@RequestBody BookRequestDTO updatedBookDTO,
                                                     @PathVariable("id") Long id) {
        Book updatedBook = BookMapper.mapToEntity(updatedBookDTO);
        updatedBook.setBookId(id);
        Book book = bookService.updateBook(updatedBook);
        BookRequestDTO updatedBookRequestDTO = BookMapper.mapToDTO(book);
        return new ResponseEntity<>(updatedBookRequestDTO, HttpStatus.OK);
    }


    @DeleteMapping("/books/{id}")
    public void deleteBook(@PathVariable Long id) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new RuntimeException("Book not found"));
        bookService.deleteBook(book.getBookId());
    }


    @PostMapping("/{bookId}/release")
    public ResponseEntity<?> releaseBook(@PathVariable Long bookId) {
        try {
            bookService.release(bookId);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{bookId}/assign")
    public ResponseEntity<?> assignBook(@PathVariable Long bookId, @RequestBody AbonementRequestDTO abonementRequestDTO) {
        Abonement abonement = abonementRepository.findByFullName(abonementRequestDTO.getFullName())
                .orElseThrow(() -> new NoSuchElementException("Abonement not found"));
        try {
            bookService.assign(bookId, abonement);
            return ResponseEntity.ok().build();
        } catch (NoSuchElementException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
