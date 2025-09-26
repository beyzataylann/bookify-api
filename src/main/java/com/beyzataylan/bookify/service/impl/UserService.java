package com.beyzataylan.bookify.service.impl;

import com.beyzataylan.bookify.dto.LoginRequest;
import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.dto.UserDTO;
import com.beyzataylan.bookify.entity.User;
import com.beyzataylan.bookify.exception.OurException;
import com.beyzataylan.bookify.repository.UserRepository;
import com.beyzataylan.bookify.service.IUserService;
import com.beyzataylan.bookify.utils.JWTUtils;
import com.beyzataylan.bookify.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public Response register(User user) {
        Response response = new Response();
        try {
            // Role default
            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }

            // Email kontrolü
            if(user.getUserEmail() == null || user.getUserEmail().isBlank()){
                throw new OurException("Email boş olamaz.");
            }

            if(userRepository.existsByUserEmail(user.getUserEmail())){
                throw new OurException(user.getUserEmail() + " adresi zaten kayıtlı!");
            }

            // Password kontrolü
            if(user.getPassword() == null || user.getPassword().isBlank()){
                throw new OurException("Şifre boş olamaz.");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            // Kaydet
            User savedUser = userRepository.save(user);

            // DTO’ya map et
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(savedUser);

            response.setStatusCode(200);
            response.setMessage("Kayıt işlemi başarıyla tamamlandı.");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(400);
            response.setMessage("Kayıt hatası: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUserEmail(),
                            loginRequest.getPassword()
                    )
            );

            User user = userRepository.findByUserEmail(loginRequest.getUserEmail())
                    .orElseThrow(() -> new OurException("Kullanıcı bulunamadı."));
            String token = jwtUtils.generateToken(user);

            response.setStatusCode(200);
            response.setMessage("Giriş başarılı.");
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 gün");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Giriş hatası: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try {
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserListDTO(userList);

            response.setStatusCode(200);
            response.setMessage("Tüm kullanıcılar başarıyla getirildi.");
            response.setUserList(userDTOList);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kullanıcılar getirilirken hata: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserLoansHistory(String userId) {
        Response response = new Response();
        try {
            User user = userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new OurException("Böyle bir kullanıcı bulunmamaktadır."));
            UserDTO userDTO = Utils.mapUserEntityToUserDTOLoans(user);

            response.setStatusCode(200);
            response.setMessage("Kullanıcının ödünç alma geçmişi başarıyla getirildi.");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Geçmiş alınamadı: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try {
            userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new OurException("Kullanıcı bulunamadı."));
            userRepository.deleteById(Long.valueOf(userId));

            response.setStatusCode(200);
            response.setMessage("Kullanıcı başarıyla silindi.");

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Silme işlemi başarısız: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserById(String userId) {
        Response response = new Response();
        try {
            User user = userRepository.findById(Long.valueOf(userId))
                    .orElseThrow(() -> new OurException("Kullanıcı bulunamadı."));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("Kullanıcı başarıyla getirildi.");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Kullanıcı alınamadı: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String userEmail) {
        Response response = new Response();
        try {
            User user = userRepository.findByUserEmail(userEmail)
                    .orElseThrow(() -> new OurException("Kullanıcı bulunamadı."));
            UserDTO userDTO = Utils.mapUserEntityToUserDTO(user);

            response.setStatusCode(200);
            response.setMessage("Kullanıcı bilgileri başarıyla getirildi.");
            response.setUser(userDTO);

        } catch (OurException e) {
            response.setStatusCode(404);
            response.setMessage("Bilgi alınamadı: " + e.getMessage());
        } catch (Exception e) {
            response.setStatusCode(500);
            response.setMessage("Sunucu hatası: " + e.getMessage());
        }
        return response;
    }
}
