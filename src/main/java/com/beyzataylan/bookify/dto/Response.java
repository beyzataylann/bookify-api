package com.beyzataylan.bookify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Response {
    private int statusCode;
    private String message;

    private String token;
    private String role;
    private String expirationTime;
    private String loansConfirmationCode;

    private BookDTO book;//Tek bir kitap nesnesi döndürmek için.
    private UserDTO user;//Tek bir kullanıcı nesnesi döndürmek için.
    private LoansDTO loans;//Tek bir ödünç nesnesi döndürmek için.
    private List<UserDTO> userList;//Çoklu kullanıcı listesi döndürmek için.
    private List<BookDTO> bookList;//Çoklu kitap listesi döndürmek için.
    private List<LoansDTO> loansList;// Çoklu ödünç listesi döndürmek için.



}


