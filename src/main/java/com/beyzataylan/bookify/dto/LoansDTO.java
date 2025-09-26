package com.beyzataylan.bookify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoansDTO {
    private long id;
    private LocalDate loansDate;
    private LocalDate returnDate;
    private UserDTO user;
    private BookDTO book;
}
