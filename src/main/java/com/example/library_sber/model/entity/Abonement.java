package com.example.library_sber.model.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@EqualsAndHashCode
@Table(name="Abonement")
public class Abonement {
    @Id
    @Column(name = "abonement_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long abonementId;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "open_date")
    private Date openDate;

    @OneToMany(mappedBy = "owner")
    @JsonManagedReference
    private List<Book> books;

//    @ManyToMany
//    @JoinTable(name = "abonement_roles",
//            joinColumns = @JoinColumn(name = "abonement_id"),
//            inverseJoinColumns = @JoinColumn(name = "role_id"))
//    private List<Role> roles = new ArrayList<>();

}
