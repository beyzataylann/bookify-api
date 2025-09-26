package com.beyzataylan.bookify.controller;

import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.entity.Loans;
import com.beyzataylan.bookify.service.ILoansService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loans")
public class LoansController {

    @Autowired
    private ILoansService loansService;


    @PostMapping("/loans-book/{bookId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveLoans(@PathVariable("bookId") String bookId, @PathVariable("userId") String userId, @RequestBody Loans loansRequest){
        Response response = loansService.saveLoans(bookId, userId, loansRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    @GetMapping("/all-loans")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllLoans(){
        Response response = loansService.getAllLoans();
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/get-by-confirmation-code/{confirmationCode}")
    public ResponseEntity<Response> getLoansByConfirmatinCode(@PathVariable("confirmationCode") String confirmationCode){
        Response response = loansService.findLoansByConfirmationCode(confirmationCode);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

    @GetMapping("/cancel/{loansId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelLoans(@PathVariable("loansId") String loansId){
        Response response = loansService.cancelLoans(loansId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }

}
