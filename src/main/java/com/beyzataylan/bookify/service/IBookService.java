package com.beyzataylan.bookify.service;

import com.beyzataylan.bookify.dto.Response;

import java.time.LocalDate;
import java.util.List;

public interface IBookService {


    Response addNewBook(String bookType, String name, String bookDescription);

    List<String> getAllBookTypes();

    Response getAllBooks();

    Response deleteBook(String bookId);

    Response updateBook(String bookId, String bookType, String name, String bookDescription);

    Response getBookById(String bookId);

    Response  getAvaliableBooksByDataAndType(LocalDate loansDate, LocalDate returnDate, String bookType);

    Response getAllAvaliableBooks();


}
