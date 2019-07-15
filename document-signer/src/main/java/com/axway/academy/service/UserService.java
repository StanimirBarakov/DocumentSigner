package com.axway.academy.service;

import com.axway.academy.model.dto.UserLoginDto;
import com.axway.academy.model.dto.UserRegisterDto;
import com.axway.academy.model.entity.User;
import com.axway.academy.model.exception.UserException;

import javax.servlet.http.HttpSession;

public interface UserService {
    User registerUser(UserRegisterDto userRegisterDto)throws UserException;
    User logUser(UserLoginDto userLoginDto, HttpSession session)throws UserException;
    void logOutUser(HttpSession session)throws UserException;

}
