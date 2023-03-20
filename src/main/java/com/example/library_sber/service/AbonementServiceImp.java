package com.example.library_sber.service;

import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Book;
import com.example.library_sber.repository.AbonementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class AbonementServiceImp implements AbonementService{

    private final AbonementRepository abonementRepository;

    private final Long THIRTYDAYS = 2592000000L;

    @Override
    public Optional<Abonement> getAbonementById(Long abonementId) {
        return abonementRepository.findById(abonementId);
    }

    @Override
    public Optional<Abonement> getAbonementByFullName(String fullName) {
        return abonementRepository.findByFullName(fullName);
    }

    @Override
    public List<Abonement> getAllAbonements() {
        return abonementRepository.findAll();
    }

    @Override
    public Abonement addAbonement(Abonement abonement) {
        return abonementRepository.save(abonement);
    }

    @Override
    public Abonement updateAbonement(Abonement abonement) {
        Optional<Abonement>repoAbonement = abonementRepository.findById(abonement.getAbonementId());
        if(repoAbonement.isEmpty()){
            throw new RuntimeException("Abonement with this id is not found!");
        }
        abonement.setAbonementId(repoAbonement.get().getAbonementId());
        return abonementRepository.save(abonement);
    }

    @Override
    public void deleteAbonement(Long abonementId) {
        Abonement abonement = abonementRepository.findById(abonementId)
                .orElseThrow(()-> new RuntimeException("Abonement is not found"));
        abonementRepository.delete(abonement);
    }

    @Override
    public List<Book> getBooksByPersonId(Long abonementId) {
        Optional<Abonement> abonement = abonementRepository.findById(abonementId);
        if (abonement.isPresent()) {
            return abonement.get().getBooks();
        } else {
            return Collections.emptyList();
        }
    }

    @Override
    public AtomicBoolean checkBookExpiration(Long abonementId) {
        Optional<Abonement> abonement = abonementRepository.findById(abonementId);
        AtomicBoolean hasExpiredBooks = new AtomicBoolean(false);
        if (abonement.isPresent()) {
            // Проверка просроченности книг
            abonement.get().getBooks().forEach(book -> {
                long diffInMillies = Math.abs(book.getTakenAt().getTime() - new Date().getTime());
                if (diffInMillies > THIRTYDAYS){
                    book.setExpired(true); // книга просрочена
                    hasExpiredBooks.set(true);
                }
            });
        }
        return hasExpiredBooks;
    }
}
