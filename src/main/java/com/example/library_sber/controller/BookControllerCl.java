package com.example.library_sber.controller;

import com.example.library_sber.model.converter.BookMapper;
import com.example.library_sber.model.dto.BookRequestDTO;
import com.example.library_sber.model.entity.Book;
import com.example.library_sber.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
// Добавить сваггеровские аннотации
public class BookControllerCl {
    private final BookService bookService;

    @GetMapping("/books")
    public ResponseEntity<List<BookRequestDTO>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        List<BookRequestDTO> bookRequestDTO = books.stream()
                .map(book -> BookMapper.mapToDTO(book))
                .collect(Collectors.toList());
        return new ResponseEntity<>(bookRequestDTO, HttpStatus.OK);
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<BookRequestDTO> getBookById(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.getBookById(id);
        if (bookOptional.isPresent()) {
            Book book = bookOptional.get();
            BookRequestDTO bookRequestDTO = BookMapper.mapToDTO(book);
            return new ResponseEntity<>(bookRequestDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/books/{title}")
    public ResponseEntity<List<BookRequestDTO>> getBookByTitle(@PathVariable String bookTitle) {
        Optional<Book> books = bookService.getBookByTitle(bookTitle);
        if (!books.isEmpty()) {
            List<BookRequestDTO> bookRequestDTOs = books.stream()
                    .map(BookMapper::mapToDTO)
                    .collect(Collectors.toList());
            return new ResponseEntity<>(bookRequestDTOs, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

//    @GetMapping("/books/{id}/owner")
//    public ResponseEntity<Abonement> getBookOwner(@PathVariable Long bookId) {
//        Abonement abonement = bookService.getBookOwner(bookId);
//        if (abonement != null) {
//            return new ResponseEntity<>(abonement, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//
//    @PostMapping("/books")
//    public ResponseEntity<Book> addBook(@RequestBody BookRequestDTO dto) {
//        Book book = BookMapper.mapToEntity(dto);
//        book.setBookId(book.getBookId());
//        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
//    }
//
//    @PutMapping("/books/{id}")
//    public ResponseEntity<BookRequestDTO> updateBook(@RequestBody BookRequestDTO updatedBookDTO,
//                                                               @PathVariable("id") Long id) {
//        Book updatedBook = BookMapper.mapToEntity(updatedBookDTO);
//        updatedBook.setBookId(id);
//        Book book = bookService.updateBook(updatedBook);
//        BookRequestDTO updatedBookRequestDTO = BookMapper.mapToDTO(book);
//        return new ResponseEntity<>(updatedBookRequestDTO, HttpStatus.OK);
//    }
//
//    @DeleteMapping("/books/{id}")
//    public void deleteBook(@PathVariable Long id) {
//        Book book = bookService.getBookById(id)
//                .orElseThrow(() -> new RuntimeException("Book not found"));
//        bookService.deleteBook(book.getBookId());
//    }
//
//
//    @PostMapping("/{bookId}/release")
//    public ResponseEntity<?> releaseBook(@PathVariable Long bookId) {
//        try {
//            bookService.release(bookId);
//            return ResponseEntity.ok().build();
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping("/{bookId}/assign")
//    public ResponseEntity<?> assignBook(@PathVariable Long bookId, @RequestBody AbonementRequestDTO abonementRequestDTO) {
//        Abonement abonement = AbonementMapper.mapToEntity(abonementRequestDTO);
//        try {
//            bookService.assign(bookId, abonement);
//            return ResponseEntity.ok().build();
//        } catch (NoSuchElementException e) {
//            return ResponseEntity.notFound().build();
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }
//    }
}
