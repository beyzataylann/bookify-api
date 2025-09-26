package com.beyzataylan.bookify.controller;

import com.beyzataylan.bookify.dto.LoginRequest;
import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.dto.UserDTO;
import com.beyzataylan.bookify.entity.User;
import com.beyzataylan.bookify.service.IUserService;
import org.apache.tomcat.util.http.Rfc6265CookieProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private IUserService userService;

    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody UserDTO userDTO) {

        Response response = new Response();

        if(userDTO.getUserEmail() == null || userDTO.getUserEmail().isBlank() ||
                userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            response.setStatusCode(400);
            response.setMessage("Email ve password zorunludur");
            return ResponseEntity.badRequest().body(response);
        }

        User user = new User();
        user.setUserEmail(userDTO.getUserEmail());
        user.setPassword(userDTO.getPassword());
        user.setUserName(userDTO.getUserName());
        user.setUserPhoneNumber(userDTO.getUserPhoneNumber());
        user.setRole(userDTO.getRole());

        response = userService.register(user);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }



    @PostMapping("/login")
    public ResponseEntity<Response> login(@RequestBody LoginRequest loginRequest){
        Response response = userService.login(loginRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
