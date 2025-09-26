package com.beyzataylan.bookify.service;

import com.beyzataylan.bookify.dto.LoginRequest;
import com.beyzataylan.bookify.dto.Response;
import com.beyzataylan.bookify.entity.User;

public interface IUserService {

    Response register(User register);

    Response login(LoginRequest loginRequest);

    Response getAllUsers();

    Response getUserLoansHistory(String UserId);

    Response deleteUser(String UserId);

    Response getUserById(String UserId);

    Response getMyInfo(String userEmail);




}
