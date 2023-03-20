package com.example.library_sber.repository;

import com.example.library_sber.model.entity.Abonement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AbonementRepository extends JpaRepository<Abonement, Long> {
    Optional<Abonement> findByFullName(String fullName);
}

