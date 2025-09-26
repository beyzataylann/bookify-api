package com.beyzataylan.bookify.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {


    private long userId ;
    private  String userEmail;
    private String userName;
    private String userPhoneNumber;
    private String role;

    //@JsonIgnore
    private String password; // Backend’de kullanılacak, frontend göremez
    private List<LoansDTO> loans = new ArrayList<>();


}
