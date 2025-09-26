package com.beyzataylan.bookify.service.impl;

import com.beyzataylan.bookify.dto.LoansDTO;
import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.entity.Book;
import com.beyzataylan.bookify.entity.Loans;
import com.beyzataylan.bookify.entity.User;
import com.beyzataylan.bookify.exception.OurException;
import com.beyzataylan.bookify.repository.BookRepository;
import com.beyzataylan.bookify.repository.LoansRepository;
import com.beyzataylan.bookify.repository.UserRepository;
import com.beyzataylan.bookify.service.IBookService;
import com.beyzataylan.bookify.service.ILoansService;
import com.beyzataylan.bookify.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LoansService implements ILoansService {

    @Autowired
    private LoansRepository loansRepository;

    @Autowired
    private IBookService bookService;

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Response saveLoans(String bookId, String userId, Loans loansRequest) {
        Response response = new Response();
        try {
            if (loansRequest.getReturnDate().isBefore(loansRequest.getLoansDate())) {
                throw new IllegalArgumentException("İade tarihi, ödünç alma tarihinden önce olamaz!");
            }

            Book book = bookRepository.findById(Long.valueOf(bookId))
                    .orElseThrow(() -> new OurException("Böyle bir kitap bulunmamaktadır."));
            User user = userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new OurException("Böyle bir kullanıcı bulunmamaktadır."));

            List<Loans> existingLoans = book.getLoans();
            if (!loansisAvaliable(loansRequest, existingLoans)) {
                throw new OurException("Kitap bu tarihlerde uygun değildir.");
            }

            loansRequest.setBook(book);
            loansRequest.setUser(user);

            String loansConfirmationCode = Utils.generateRandomConfirmationCode(10);
            loansRequest.setLoansConfirmationCode(loansConfirmationCode);

            loansRepository.save(loansRequest);

            response.setStatusCode(200);
            response.setMessage("Ödünç alma işlemi başarıyla kaydedildi.");
            response.setLoansConfirmationCode(loansConfirmationCode);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kayıt işlemi başarısız: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public boolean loansisAvaliable(Loans loansRequest, List<Loans> existingLoans) {
        return existingLoans.stream()
                .noneMatch(existingLoan ->
                        loansRequest.getLoansDate().equals(existingLoan.getLoansDate())
                                || (loansRequest.getReturnDate().isBefore(existingLoan.getReturnDate())
                                && loansRequest.getReturnDate().isAfter(existingLoan.getLoansDate())
                                && loansRequest.getLoansDate().isBefore(existingLoan.getLoansDate()))
                                || (loansRequest.getReturnDate().equals(existingLoan.getReturnDate())
                                && loansRequest.getLoansDate().isBefore(existingLoan.getLoansDate()))
                                || (loansRequest.getReturnDate().isAfter(existingLoan.getReturnDate())
                                && loansRequest.getLoansDate().isBefore(existingLoan.getReturnDate()))
                                || (loansRequest.getLoansDate().equals(existingLoan.getReturnDate())
                                && loansRequest.getReturnDate().equals(existingLoan.getLoansDate()))
                );
    }

    @Override
    public Response findLoansByConfirmationCode(String confirmationCode) {
        Response response = new Response();
        try {
            Loans loans = loansRepository.findByLoansConfirmationCode(confirmationCode)
                    .orElseThrow(() -> new OurException("Bu onay kodu ile kayıtlı işlem bulunamadı."));

            response.setStatusCode(200);
            response.setMessage("Kayıt başarıyla bulundu.");
            response.setLoansConfirmationCode(loans.getLoansConfirmationCode());

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kayıt bulunamadı: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllLoans() {
        Response response = new Response();
        try {
            List<Loans> loansList = loansRepository.findAll();
            List<LoansDTO> loansDTOList = Utils.mapLoansListEntityToLoansListDTO(loansList);

            response.setStatusCode(200);
            response.setMessage("Tüm ödünç kayıtları başarıyla getirildi.");
            response.setLoansList(loansDTOList);

        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Kayıtlar alınırken hata oluştu: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response cancelLoans(String loansId) {
        Response response = new Response();
        try {
            Loans loan = loansRepository.findById(Long.valueOf(loansId))
                    .orElseThrow(() -> new OurException("Bu ID ile bir kayıt bulunamadı."));

            loansRepository.delete(loan);

            response.setStatusCode(200);
            response.setMessage("Ödünç alma işlemi başarıyla iptal edildi.");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("İptal işlemi başarısız: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }
}
