package com.beyzataylan.bookify.service;

import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.entity.Loans;

import java.util.List;

public interface ILoansService {



    Response saveLoans(String bookId, String userId, Loans loansRequest);

    boolean loansisAvaliable(Loans loansRequest, List<Loans> existingLoans);

    Response findLoansByConfirmationCode(String confirmationCode);

    Response getAllLoans();

    Response cancelLoans(String loansId);
}
