package com.example.library_sber.controller;

import com.example.library_sber.model.converter.AbonementMapper;
import com.example.library_sber.model.dto.AbonementRequestDTO;
import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.service.AbonementService;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/librarian")
@RequiredArgsConstructor
// Добавить сваггеровские аннотации
public class AbonementControllerLibr {
    private final AbonementService abonementService;

    @GetMapping("/abonements")
    public ResponseEntity<List<AbonementRequestDTO>> getAllAbonements() {
        List<Abonement> abonements = abonementService.getAllAbonements();
        List<AbonementRequestDTO> abonementRequestDTOs = abonements.stream()
                .map(abonement -> AbonementMapper.mapToDTO(abonement))
                .collect(Collectors.toList());
        return new ResponseEntity<>(abonementRequestDTOs, HttpStatus.OK);
    }

    @GetMapping("/abonements/{id}")
    public ResponseEntity<AbonementRequestDTO> getAbonementById(@PathVariable Long id) {
        Optional<Abonement> abonementOptional = abonementService.getAbonementById(id);
        if (abonementOptional.isPresent()) {
            Abonement abonement = abonementOptional.get();
            AbonementRequestDTO abonementRequestDTO = AbonementMapper.mapToDTO(abonement);
            return new ResponseEntity<>(abonementRequestDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/abonements/{name}")
    public ResponseEntity<AbonementRequestDTO> getAbonementByName(@PathVariable String name) {
        Optional<Abonement> abonementOptional = abonementService.getAbonementByFullName(name);
        if (abonementOptional.isPresent()) {
            Abonement abonement = abonementOptional.get();
            AbonementRequestDTO abonementRequestDTO = AbonementMapper.mapToDTO(abonement);
            return new ResponseEntity<>(abonementRequestDTO, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/abonements")
    public ResponseEntity<Abonement> addAbonement(@RequestBody AbonementRequestDTO dto) {
        Abonement abonement = AbonementMapper.mapToEntity(dto);
        abonement.setAbonementId(abonement.getAbonementId());
        return new ResponseEntity<>(abonementService.addAbonement(abonement), HttpStatus.CREATED);
    }

    @PutMapping("/abonements/{id}")
    public ResponseEntity<AbonementRequestDTO> updateAbonement(@RequestBody AbonementRequestDTO updatedAbonementDTO,
                                                               @PathVariable("id") Long id) {
        Abonement updatedAbonement = AbonementMapper.mapToEntity(updatedAbonementDTO);
        updatedAbonement.setAbonementId(id);
        Abonement abonement = abonementService.updateAbonement(updatedAbonement);
        AbonementRequestDTO updatedAbonementRequestDTO = AbonementMapper.mapToDTO(abonement);
        return new ResponseEntity<>(updatedAbonementRequestDTO, HttpStatus.OK);
    }

    @DeleteMapping("/abonements/{id}")
    public void deleteAbonement(@PathVariable Long id) {
        Abonement abonement = abonementService.getAbonementById(id)
                .orElseThrow(() -> new RuntimeException("Abonement not found"));
        abonementService.deleteAbonement(abonement.getAbonementId());
    }
}
