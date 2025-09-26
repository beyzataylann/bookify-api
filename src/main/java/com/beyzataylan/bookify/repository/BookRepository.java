package com.beyzataylan.bookify.repository;

import com.beyzataylan.bookify.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {


    @Query("SELECT DISTINCT b.bookType FROM Book b")
    List<String> findDistinctBookTypes();

    @Query("SELECT b FROM Book b " +
            "WHERE b.bookType = :bookType " +
            "AND b.id NOT IN ( " +
            "   SELECT br.book.id FROM Loans br " +
            "   WHERE (br.loansDate <= :returnDate ) " +
            ")")
    List<Book> findAvaliableBooksByDatesAndTypes(LocalDate loansDate, LocalDate returnDate,String bookType) ;

    @Query("SELECT b FROM Book b WHERE b.id NOT IN (SELECT br.book.id FROM Loans br)")
    List<Book> getAllAvailableBooks();

}

