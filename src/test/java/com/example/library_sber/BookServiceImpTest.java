package com.example.library_sber;

import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Book;
import com.example.library_sber.repository.BookRepository;
import com.example.library_sber.service.BookServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceImpTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookServiceImp bookServiceImp;

    @Test
    public void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        book.setBookId(1L);
        book.setBookTitle("Book1");
        book.setQuantity(2L);
        bookServiceImp.addBook(book);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        Optional<Book>result = bookServiceImp.getBookById(bookId);
        assertTrue(result.isPresent());
        assertEquals(result.get(), book);
    }

    @Test
    public void testGetBookByTitle() {
        String bookTitle = "Book1";
        Book book = new Book();
        book.setBookId(1L);
        book.setBookTitle("Book1");
        book.setQuantity(2L);
        bookServiceImp.addBook(book);
        when(bookRepository.findByBookTitle(bookTitle)).thenReturn(Optional.of(book));

        Optional<Book>result = bookServiceImp.getBookByTitle(bookTitle);
        assertTrue(result.isPresent());
        assertEquals(result.get(), book);
    }

    @Test
    public void testGetAllBooks() {
        Book book1 = new Book();
        book1.setBookId(1L);
        book1.setBookTitle("Book1");
        book1.setQuantity(2L);

        Book book2 = new Book();
        book2.setBookId(1L);
        book2.setBookTitle("Book1");
        book2.setQuantity(2L);

        List<Book>books= Arrays.asList(book1,book2);
        when(bookRepository.findAll()).thenReturn(books);

        List<Book>result= bookServiceImp.getAllBooks();
        assertEquals(books, result);
    }

    @Test
    public void testAddBook(){
        Book book = new Book();
        book.setBookId(1L);
        book.setBookTitle("Book1");
        book.setQuantity(1L);
        when(bookRepository.save(book)).thenReturn(book);

        Book result = bookServiceImp.addBook(book);
        assertEquals(book,result);
    }

    @Test
    public void testUpdateBook(){
        Book existingBook = new Book();
        existingBook.setBookId(1L);
        existingBook.setBookTitle("Book1");
        existingBook.setQuantity(2L);

        Book updatedBook = new Book();
        updatedBook.setBookId(1L);
        updatedBook.setBookTitle("Book2");
        updatedBook.setQuantity(2L);

        Long bookId = 1L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(updatedBook)).thenReturn(updatedBook);

        Book result = bookServiceImp.updateBook(updatedBook);
        assertEquals(updatedBook, result);
    }

    @Test
    public void testUpdateBookWithNonexistentId() {
        Long bookId = 1L;
        Book updatedBook = new Book();
        updatedBook.setBookId(1L);
        updatedBook.setBookTitle("Jane Smith");
        updatedBook.setQuantity(1L);

        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> bookServiceImp.updateBook(updatedBook));
    }

    @Test
    public void deleteBook_shouldDeleteExistingBook() {
        Book book = new Book();
        book.setBookId(1L);
        Mockito.when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookServiceImp.deleteBook(1L);

        Mockito.verify(bookRepository, Mockito.times(1)).delete(book);
        Mockito.verify(bookRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(bookRepository);
        Mockito.reset(bookRepository);
    }

    @Test
    public void deleteBook_shouldThrowExceptionIfBookNotFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> bookServiceImp.deleteBook(1L));
    }

    @Test
    public void testGetBookOwnerWithExistingBookAndOwner(){
        Book book = new Book();
        Abonement abonement = new Abonement();
        book.setOwner(abonement);

        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        Long bookId=1L;
        Abonement result = bookServiceImp.getBookOwner(bookId);
        assertEquals(result, abonement);
    }

    @Test
    public void testGetBookOwnerWithNullId() {
        Long bookId = null;
        Abonement result = bookServiceImp.getBookOwner(bookId);
        assertNull(result);
    }

    @Test
    public void testGetBookOwnerWithNonExistingBook() {
        Long bookId = 10L;
        when(bookRepository.findById(bookId)).thenReturn(Optional.empty());
        Abonement result = bookServiceImp.getBookOwner(bookId);
        assertNull(result);
    }

    @Test
    public void testGetBookOwnerWithNoOwner() {
        Long bookId = 7L;
        Book bookWithNoOwner = new Book();
        bookWithNoOwner.setBookId(bookId);
        bookWithNoOwner.setBookTitle("Book1");
        bookWithNoOwner.setOwner(null);
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(bookWithNoOwner));

        Abonement result = bookServiceImp.getBookOwner(bookId);

        verify(bookRepository).findById(bookId);
        assertNull(bookWithNoOwner.getOwner());
        assertNull(result);
    }

    @Test
    public void testReleaseBook() {
        Long bookId = 123L;
        Book book = new Book();
        book.setOwner(new Abonement());
        book.setTakenAt(new Date());
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookServiceImp.release(bookId);

        verify(bookRepository, Mockito.times(1)).findById(bookId);
        assertNull(book.getOwner());
        assertNull(book.getTakenAt());
    }

    @Test
    public void testAssignBook() {
        Long bookId = 123L;
        Abonement abonement = new Abonement();
        Book book = new Book();
        when(bookRepository.findById(bookId)).thenReturn(Optional.of(book));

        bookServiceImp.assign(bookId, abonement);

        verify(bookRepository, Mockito.times(1)).findById(bookId);
        assertEquals(abonement, book.getOwner());
        assertNotNull(book.getTakenAt());
    }

}
