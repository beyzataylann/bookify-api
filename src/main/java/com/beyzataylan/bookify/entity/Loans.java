package com.beyzataylan.bookify.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Loans")
public class Loans {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loansId")
    private long id;

    @NotNull(message = "Tarih boş bırakılamaz!")
    @Column(name = "loansDate")
    private LocalDate loansDate;

    @Override
    public String toString() {
        return "Loans{" +
                ", id=" + id +
                ", loansDate =" + loansDate +
                ", returnDate=" + returnDate +
                '}';
    }

    @NotNull(message = "Tarih boş bırakılamaz!")
    @Column(name = "returnDate")
    private LocalDate returnDate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    @ToString.Exclude
    private  User user;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @ToString.Exclude
    private Book book;

    private String loansConfirmationCode;
}
