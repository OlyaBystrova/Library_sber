package com.example.library_sber;

import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Book;
import com.example.library_sber.repository.AbonementRepository;
import com.example.library_sber.service.AbonementServiceImp;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AbonementServiceImpTest {

    @Mock
    private AbonementRepository abonementRepository;

    @InjectMocks
    private AbonementServiceImp abonementServiceImp;

    @Test
    public void testGetAbonementById() {
        Long abonementId = 1L;
        Abonement abonement = new Abonement();
        abonement.setAbonementId(1L);
        abonement.setFullName("John Doe");
        abonement.setBooks(Collections.emptyList());
        abonementServiceImp.addAbonement(abonement);
        when(abonementRepository.findById(abonementId)).thenReturn(Optional.of(abonement));

        Optional<Abonement> result = abonementServiceImp.getAbonementById(abonementId);

        assertTrue(result.isPresent());
        assertEquals(abonement, result.get());
    }

    @Test
    public void testGetAbonementByFullName() {
        String fullName = "John Doe";
        Abonement abonement = new Abonement();
        abonement.setAbonementId(1L);
        abonement.setFullName("John Doe");
        abonement.setBooks(Collections.emptyList());
        when(abonementRepository.findByFullName(fullName)).thenReturn(Optional.of(abonement));

        Optional<Abonement> result = abonementServiceImp.getAbonementByFullName(fullName);

        assertTrue(result.isPresent());
        assertEquals(abonement, result.get());
    }

    @Test
    public void testGetAllAbonements() {
        Abonement abonement1 = new Abonement();
        abonement1.setAbonementId(1L);
        abonement1.setFullName("John Doe");
        abonement1.setBooks(Collections.emptyList());

        Abonement abonement2 = new Abonement();
        abonement2.setAbonementId(2L);
        abonement2.setFullName("Jane Smith");
        abonement2.setBooks(Collections.emptyList());

        List<Abonement> abonements = Arrays.asList(abonement1, abonement2);
        when(abonementRepository.findAll()).thenReturn(abonements);

        List<Abonement> result = abonementServiceImp.getAllAbonements();

        assertEquals(abonements, result);
    }

    @Test
    public void testAddAbonement() {
        Abonement abonement = new Abonement();
        abonement.setAbonementId(1L);
        abonement.setFullName("John Doe");
        abonement.setBooks(Collections.emptyList());

        when(abonementRepository.save(abonement)).thenReturn(abonement);

        Abonement result = abonementServiceImp.addAbonement(abonement);

        assertEquals(abonement, result);
    }

    @Test
    public void testUpdateAbonement() {
        Abonement existingAbonement = new Abonement();
        existingAbonement.setAbonementId(1L);
        existingAbonement.setFullName("John Doe");
        existingAbonement.setBooks(Collections.emptyList());

        Abonement updatedAbonement = new Abonement();
        updatedAbonement.setAbonementId(1L);
        updatedAbonement.setFullName("Jane Smith");
        updatedAbonement.setBooks(Collections.emptyList());

        Long abonementId = 1L;
        when(abonementRepository.findById(abonementId)).thenReturn(Optional.of(existingAbonement));
        when(abonementRepository.save(updatedAbonement)).thenReturn(updatedAbonement);

        Abonement result = abonementServiceImp.updateAbonement(updatedAbonement);

        assertEquals(updatedAbonement, result);
    }

    @Test
    public void testUpdateAbonementWithNonexistentId() {
        Long abonementId = 1L;
        Abonement updatedAbonement = new Abonement();
        updatedAbonement.setAbonementId(1L);
        updatedAbonement.setFullName("Jane Smith");
        updatedAbonement.setBooks(Collections.emptyList());

        when(abonementRepository.findById(abonementId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> abonementServiceImp.updateAbonement(updatedAbonement));
    }

    @Test
    public void deleteAbonement_shouldDeleteExistingAbonement() {
        Abonement abonement = new Abonement();
        abonement.setAbonementId(1L);
        Mockito.when(abonementRepository.findById(1L)).thenReturn(Optional.of(abonement));

        abonementServiceImp.deleteAbonement(1L);

        Mockito.verify(abonementRepository, Mockito.times(1)).delete(abonement);
        Mockito.verify(abonementRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(abonementRepository);
        Mockito.reset(abonementRepository);
    }

    @Test
    public void deleteAbonement_shouldThrowExceptionIfAbonementNotFound() {
        when(abonementRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> abonementServiceImp.deleteAbonement(1L));
    }



    @Test
    public void getBooksByPersonId_shouldReturnListOfBooks() {
        Abonement abonement = new Abonement();
        Book book1 = new Book();
        Book book2 = new Book();
        List<Book> expectedBooks = Arrays.asList(book1, book2);
        abonement.setBooks(expectedBooks);
        Mockito.when(abonementRepository.findById(1L)).thenReturn(Optional.of(abonement));

        List<Book> actualBooks = abonementServiceImp.getBooksByPersonId(1L);

        Mockito.verify(abonementRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(abonementRepository);
        Assert.assertEquals(expectedBooks, actualBooks);
    }

    @Test
    public void getBooksByPersonId_shouldReturnEmptyListIfAbonementNotFound() {
        Mockito.when(abonementRepository.findById(1L)).thenReturn(Optional.empty());

        List<Book> actualBooks = abonementServiceImp.getBooksByPersonId(1L);

        Mockito.verify(abonementRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(abonementRepository);
        Assert.assertEquals(Collections.emptyList(), actualBooks);
    }

    @Test
    public void checkBookExpiration_shouldReturnFalseIfNoBooksAreExpired() {
        Abonement abonement = new Abonement();
        Book book1 = new Book();
        book1.setTakenAt(new Date()); // Set takenAt to current date/time
        Book book2 = new Book();
        book2.setTakenAt(new Date()); // Set takenAt to current date/time
        List<Book> books = Arrays.asList(book1, book2);
        abonement.setBooks(books);
        Mockito.when(abonementRepository.findById(1L)).thenReturn(Optional.of(abonement));

        AtomicBoolean hasExpiredBooks = abonementServiceImp.checkBookExpiration(1L);

        Mockito.verify(abonementRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(abonementRepository);
        Assert.assertFalse(hasExpiredBooks.get());
        Assert.assertFalse(book1.isExpired());
        Assert.assertFalse(book2.isExpired());
    }

    @Test
    public void checkBookExpiration_shouldReturnTrueIfAtLeastOneBookIsExpired() {
        Abonement abonement = new Abonement();
        Book book1 = new Book();
        book1.setTakenAt(new Date(System.currentTimeMillis() - TimeUnit.DAYS.toMillis(31))); // set as expired
        Book book2 = new Book();
        book2.setTakenAt(new Date());
        List<Book> books = Arrays.asList(book1, book2);
        abonement.setBooks(books);
        Mockito.when(abonementRepository.findById(1L)).thenReturn(Optional.of(abonement));

        AtomicBoolean hasExpiredBooks = abonementServiceImp.checkBookExpiration(1L);

        Mockito.verify(abonementRepository, Mockito.times(1)).findById(1L);
        Mockito.verifyNoMoreInteractions(abonementRepository);
        Assert.assertTrue(hasExpiredBooks.get());
        Assert.assertTrue(book1.isExpired());
        Assert.assertFalse(book2.isExpired());
    }
}



