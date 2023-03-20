package com.example.library_sber.service;

import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Book;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

public interface AbonementService {
    Optional<Abonement> getAbonementById(Long abonementId);
    Optional<Abonement> getAbonementByFullName(String fullName);
    List<Abonement> getAllAbonements();
    Abonement addAbonement(Abonement abonement);
    Abonement updateAbonement(Abonement abonement);
    void deleteAbonement(Long abonementId);
    List<Book> getBooksByPersonId(Long abonementId);
    AtomicBoolean checkBookExpiration(Long abonementId);
}
