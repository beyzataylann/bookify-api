package com.beyzataylan.bookify.utils;

import com.beyzataylan.bookify.dto.BookDTO;
import com.beyzataylan.bookify.dto.LoansDTO;
import com.beyzataylan.bookify.dto.UserDTO;
import com.beyzataylan.bookify.entity.Book;
import com.beyzataylan.bookify.entity.Loans;
import com.beyzataylan.bookify.entity.User;

import java.security.SecureRandom;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {



    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIJKLMNOPRSTUVWYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static  String generateRandomConfirmationCode(int length){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < length ; i++){
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEntityToUserDTO(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setUserName(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setUserPhoneNumber(user.getUserPhoneNumber());
        return userDTO;
    }


    public static BookDTO mapBookEntityToBookDTO(Book book){

        BookDTO bookDTO = new BookDTO();
        bookDTO.setName(book.getName());
        bookDTO.setId(book.getId());
        bookDTO.setBookDescription(book.getBookDescription());
        bookDTO.setBookType(book.getBookType());

        if(book.getLoans() != null){
            bookDTO.setLoans(book.getLoans().stream().map(Utils::mapLoansEntityToLoansDTO).collect(Collectors.toList()));
        }

        return bookDTO;
    }

    public static LoansDTO mapLoansEntityToLoansDTO(Loans loan){

        LoansDTO loansDTO = new LoansDTO();
        loansDTO.setId(loan.getId());
        loansDTO.setLoansDate(loan.getLoansDate());
        return loansDTO;
    }

    public static UserDTO mapUserEntityToUserDTOLoans(User user){

        UserDTO userDTO = new UserDTO();
        userDTO.setUserId(user.getUserId());
        userDTO.setUserEmail(user.getUserEmail());
        userDTO.setUserName(user.getUsername());
        userDTO.setRole(user.getRole());
        userDTO.setUserPhoneNumber(user.getUserPhoneNumber());

        if (user.getLoans() != null && !user.getLoans().isEmpty()) {
            userDTO.setLoans(user.getLoans().stream()
                    .map(Utils::mapLoansEntityToLoansDTOPlusLoansBook)
                    .collect(Collectors.toList()));
        }

        return userDTO;
    }

    public static LoansDTO mapLoansEntityToLoansDTOPlusLoansBook(Loans loan){

        LoansDTO loansDTO = new LoansDTO();
        loansDTO.setId(loan.getId());
        loansDTO.setLoansDate(loan.getLoansDate());

        if(loan.getBook() != null){

            BookDTO bookDTO = new BookDTO();
            bookDTO.setName(loan.getBook().getName());
            bookDTO.setId(loan.getBook().getId());
            bookDTO.setBookDescription(loan.getBook().getBookDescription());
            bookDTO.setBookType(loan.getBook().getBookType());
        }
        return loansDTO;
    }


    public static List<UserDTO> mapUserListEntityToUserListDTO(List<User> userList){

        return userList.stream().map(Utils::mapUserEntityToUserDTO).collect(Collectors.toList());
    }

    public static List<BookDTO> mapBookListEntityToBookListDTO(List<Book> bookList){

        return bookList.stream().map(Utils::mapBookEntityToBookDTO).collect(Collectors.toList());
    }

    public static List<LoansDTO> mapLoansListEntityToLoansListDTO(List<Loans> loansList){

        return loansList.stream().map(Utils::mapLoansEntityToLoansDTO).collect(Collectors.toList());
    }

}
