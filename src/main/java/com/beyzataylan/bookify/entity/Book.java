package com.beyzataylan.bookify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;
    @Column(name = "bookType")
    private String bookType;
    @Column(name = "bookDescription")
    private String bookDescription;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @ToString.Exclude
    private List<Loans> loans = new ArrayList<>();

    @Override
    public String toString() {
        return "Book{" +
                "bookDescription='" + bookDescription + '\'' +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", bookType='" + bookType + '\'' +

                '}';
    }
}
