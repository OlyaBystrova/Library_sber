package com.example.library_sber.controller;

import com.example.library_sber.model.converter.BookMapper;
import com.example.library_sber.model.dto.BookRequestDTO;
import com.example.library_sber.model.entity.Book;
import com.example.library_sber.service.AbonementService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
@RequiredArgsConstructor
@Api("Operations with UI - Swagger")
@ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "Client error"),
        @ApiResponse(responseCode = "500", description = "Server error")
})
public class AbonementControllerCl {
    private final AbonementService abonementService;

    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful")
    })
    @Operation(summary = "Getting the list of books", description = "Getting the list of books by client's id")
    @GetMapping("/{abonementId}/books")
    public ResponseEntity<List<BookRequestDTO>> getBooksByPersonId(@PathVariable Long abonementId) {
        List<Book> books = abonementService.getBooksByPersonId(abonementId);
        List<BookRequestDTO> bookDtos = books.stream().map(BookMapper::mapToDTO).collect(Collectors.toList());
        return ResponseEntity.ok(bookDtos);
    }
}
