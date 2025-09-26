package com.beyzataylan.bookify.controller;

import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.service.IBookService;
import com.beyzataylan.bookify.service.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private IBookService bookService;

    @Autowired
    private ILoansService LoansService;

    @PostMapping("/add-book")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewBook(

        @RequestParam(value = "bookType", required = false)String bookType,
        @RequestParam(value = "name", required = false)String name,
        @RequestParam(value = "bookDescription", required = false)String bookDescription
        ){

        if(bookType == null || bookType.isBlank() || bookDescription == null ||bookDescription.isBlank() || name == null || name.isBlank()){

            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Lütfen  bilgileri dolduunuz!");
        }
        Response response = bookService.addNewBook(bookType,name,bookDescription);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @GetMapping("/allBooks")
    public ResponseEntity<Response> getAllBooks(){
        Response response = bookService.getAllBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/types")
    public List<String> getBookTypes(){
        return bookService.getAllBookTypes();
    }

    @GetMapping("/book-by-id/{bookId}")
    public ResponseEntity<Response> getRoomById(@PathVariable("bookId") String bookId){
        Response response = bookService.getBookById(bookId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @GetMapping("/all-available-books")
    public ResponseEntity<Response> getAvaliableBooks(){
        Response response = bookService.getAllAvaliableBooks();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }


    @PostMapping("/available-books-by-date-and-type")
    public ResponseEntity<Response> getAvaliableBooksByDateAndType(

            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate loansDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate returnDate,
            @RequestParam(required = false) String bookType
    ){

        if(loansDate == null || bookType == null || bookType.isBlank() || returnDate == null){

            Response response = new Response();
            response.setStatusCode(400);
            response.setMessage("Lütfen  bilgileri dolduunuz!");
        }
        Response response = bookService.getAvaliableBooksByDataAndType(loansDate,returnDate,bookType);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @PostMapping("/update/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> addNewBook( @PathVariable("bookId") String bookId,

            @RequestParam(value = "bookType", required = false)String bookType,
            @RequestParam(value = "name", required = false)String name,
            @RequestParam(value = "bookDescription", required = false)String bookDescription
    ){
            Response response = bookService.updateBook(bookId,  bookType,  name, bookDescription);
            return ResponseEntity.status(response.getStatusCode()).body(response);

    }

    @DeleteMapping("/delete/{bookId}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> deleteRoom(@PathVariable("userId") String userId) {
        Response response = bookService.deleteBook(userId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



}
