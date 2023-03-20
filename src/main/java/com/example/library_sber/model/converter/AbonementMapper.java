package com.example.library_sber.model.converter;

import com.example.library_sber.model.dto.AbonementRequestDTO;
import com.example.library_sber.model.entity.Abonement;
import com.example.library_sber.model.entity.Role;
import com.example.library_sber.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public final class AbonementMapper {

    private static RoleRepository roleRepository;

    public static Abonement mapToEntity(AbonementRequestDTO abonementRequestDTO) {
        Abonement abonement = new Abonement();
        List<Role> rolesDto = abonementRequestDTO.getRoles() == null
                ? null
                : roleRepository.findByIdIn(abonementRequestDTO.getRoles().stream()
                .map(Long::valueOf)
                .collect(Collectors.toList()));
        abonement.setFullName(abonementRequestDTO.getFullName());
        abonement.setOpenDate(abonementRequestDTO.getOpenDate());
        abonement.setRoles(rolesDto);
        return abonement;
    }

    public static AbonementRequestDTO mapToDTO(Abonement abonement) {
        AbonementRequestDTO abonementRequestDTO = new AbonementRequestDTO();
        List<String> roles = abonement.getRoles() == null
                ? null
                : abonement.getRoles().stream()
                .map(Role::getId)
                .map(String::valueOf)
                .collect(Collectors.toList());
        abonementRequestDTO.setFullName(abonement.getFullName());
        abonementRequestDTO.setOpenDate(abonement.getOpenDate());
        abonementRequestDTO.setRoles(roles);
        return abonementRequestDTO;
    }
}
