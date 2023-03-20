package com.example.library_sber.model.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long bookId;

    @Column(name = "book_title")
    private String bookTitle;

    @Column(name = "publication_date")
    private Date publicationDate;

    @Column(name = "quantity")
    private Long quantity;

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "abonement_id")
    private Abonement owner;

    @Column(name = "taken_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date takenAt;

    @Transient
    private boolean expired;
}
