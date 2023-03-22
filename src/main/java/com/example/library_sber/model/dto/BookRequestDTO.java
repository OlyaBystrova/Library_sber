package com.example.library_sber.model.dto;

import lombok.Data;

import java.util.Date;

@Data
public class BookRequestDTO{
//    Long bookId;
    String bookTitle;
    Date publicationDate;
    Long quantity;
}

