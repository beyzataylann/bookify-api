package com.beyzataylan.bookify.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginRequest {

    @NotBlank(message = "Kullanıcı Adı alanı boş bırakılamaz!")
    private String userEmail;
    @NotBlank(message = "Kullanıcı Adı alanı boş bırakılamaz!")
    private String password;

}
