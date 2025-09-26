package com.beyzataylan.bookify.service.impl;

import com.beyzataylan.bookify.dto.BookDTO;
import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.entity.Book;
import com.beyzataylan.bookify.exception.OurException;
import com.beyzataylan.bookify.repository.BookRepository;
import com.beyzataylan.bookify.repository.LoansRepository;
import com.beyzataylan.bookify.service.IBookService;
import com.beyzataylan.bookify.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookService implements IBookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private LoansRepository loansRepository;

    @Override
    public Response addNewBook(String bookType, String name, String bookDescription) {
        Response response = new Response();
        try {
            Book book = new Book();
            book.setName(name);
            book.setBookType(bookType);
            book.setBookDescription(bookDescription);

            Book savedBook = bookRepository.save(book);
            BookDTO bookDTO = Utils.mapBookEntityToBookDTO(savedBook);

            response.setStatusCode(200);
            response.setMessage("Kitap başarıyla eklendi.");
            response.setBook(bookDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kitap eklenirken hata: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllBookTypes() {
        return bookRepository.findDistinctBookTypes();
    }

    @Override
    public Response getAllBooks() {
        Response response = new Response();
        try {
            List<Book> booksList = bookRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
            List<BookDTO> bookDTOList = Utils.mapBookListEntityToBookListDTO(booksList);

            response.setStatusCode(200);
            response.setMessage("Tüm kitaplar başarıyla getirildi.");
            response.setBookList(bookDTOList);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kitaplar getirilirken hata: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteBook(String bookId) {
        Response response = new Response();
        try {
            Book book = bookRepository.findById(Long.valueOf(bookId))
                    .orElseThrow(() -> new OurException("Kitap ID bulunamadı!"));
            bookRepository.deleteById(Long.valueOf(bookId));

            response.setStatusCode(200);
            response.setMessage("Kitap başarıyla silindi.");
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kitap silinirken hata: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateBook(String bookId, String bookType, String name, String bookDescription) {
        Response response = new Response();
        try {
            Book book = bookRepository.findById(Long.valueOf(bookId))
                    .orElseThrow(() -> new OurException("Kitap ID bulunamadı!"));

            if (bookType != null) book.setBookType(bookType);
            if (name != null) book.setName(name);
            if (bookDescription != null) book.setBookDescription(bookDescription);

            Book savedBook = bookRepository.save(book);
            BookDTO bookDTO = Utils.mapBookEntityToBookDTO(savedBook);

            response.setStatusCode(200);
            response.setMessage("Kitap başarıyla güncellendi.");
            response.setBook(bookDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kitap güncellenirken hata: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getBookById(String bookId) {
        Response response = new Response();
        try {
            Book book = bookRepository.findById(Long.valueOf(bookId))
                    .orElseThrow(() -> new OurException("Kitap ID bulunamadı!"));

            BookDTO bookDTO = Utils.mapBookEntityToBookDTO(book);

            response.setStatusCode(200);
            response.setMessage("Kitap başarıyla bulundu.");
            response.setBook(bookDTO);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kitap bulunamadı: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvaliableBooksByDataAndType(LocalDate loansDate, LocalDate returnDate, String bookType) {
        Response response = new Response();
        try {
            List<Book> bookList = bookRepository.findAvaliableBooksByDatesAndTypes(loansDate, returnDate, bookType);
            List<BookDTO> bookDTOList = Utils.mapBookListEntityToBookListDTO(bookList);

            response.setStatusCode(200);
            response.setMessage("Uygun kitaplar başarıyla getirildi.");
            response.setBookList(bookDTOList);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Uygun kitaplar bulunamadı: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvaliableBooks() {
        Response response = new Response();
        try {
            List<Book> bookList = bookRepository.getAllAvailableBooks();
            List<BookDTO> bookDTOList = Utils.mapBookListEntityToBookListDTO(bookList);

            response.setStatusCode(200);
            response.setMessage("Tüm uygun kitaplar başarıyla getirildi.");
            response.setBookList(bookDTOList);
        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Uygun kitap bulunamadı: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }
}
