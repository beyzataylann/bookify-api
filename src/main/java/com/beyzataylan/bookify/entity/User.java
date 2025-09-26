package com.beyzataylan.bookify.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId")
    private long userId ;

    @NotBlank(message = "Email alanı boş bırakılamaz!")
    @Column(name = "userEmail")

    private  String userEmail;

    @NotBlank(message = "Kullanıcı adı alanı boş bırakılamaz!")
    @Column(name = "userName")
    private String userName;

    @NotBlank(message = "Telefon Numarası alanı boş bırakılamaz!")
    @Column(name = "userPhoneNumber")
    private String userPhoneNumber;

    @NotBlank(message = "Şifre boş bırakılamaz!")
    @Column(name = "password")
    @JsonIgnore
    private String password;

    @Column(name = "role")
    //@Enumerated(EnumType.STRING)
    private String role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Loans> loans = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role));

}

    @Override
    public String getUsername() {
        return userEmail;  //kullanıcı adi email
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; //hesap süresiz
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; //hesap kilitli değil
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; //şifre her zaman geçerli
    }

    @Override
    public boolean isEnabled() {
        return true; //hesap aktif
    }

}
