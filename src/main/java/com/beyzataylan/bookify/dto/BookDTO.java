package com.beyzataylan.bookify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private long id;
    private String name;
    private String bookType;
    private String bookDescription;
    private List<LoansDTO> loans = new ArrayList<>();
}
