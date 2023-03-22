package com.example.library_sber.model.converter;

import com.example.library_sber.model.dto.BookRequestDTO;
import com.example.library_sber.model.entity.Book;

public final class BookMapper {

    public static Book mapToEntity(BookRequestDTO bookRequestDTO){
        Book book = new Book();
        book.setBookTitle(bookRequestDTO.getBookTitle());
        book.setPublicationDate(bookRequestDTO.getPublicationDate());
        book.setQuantity(bookRequestDTO.getQuantity());
        return book;
    }

    public static BookRequestDTO mapToDTO(Book book){
        BookRequestDTO bookRequestDTO = new BookRequestDTO();
        bookRequestDTO.setBookTitle(book.getBookTitle());
        bookRequestDTO.setPublicationDate(book.getPublicationDate());
        bookRequestDTO.setQuantity(book.getQuantity());
        return bookRequestDTO;
    }
}
