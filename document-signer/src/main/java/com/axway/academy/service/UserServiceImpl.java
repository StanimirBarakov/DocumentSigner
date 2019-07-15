package com.axway.academy.service;

import com.axway.academy.model.dao.UserDao;
import com.axway.academy.model.dto.UserLoginDto;
import com.axway.academy.model.dto.UserRegisterDto;
import com.axway.academy.model.entity.User;
import com.axway.academy.model.exception.UserException;
import com.axway.academy.util.BCryptUtil;
import com.axway.academy.util.Constants;

import javax.servlet.http.HttpSession;

public class UserServiceImpl implements UserService {

    @Override
    public User registerUser(UserRegisterDto userRegisterDto) throws UserException {
        User user = new User();
        UserDao userDao = new UserDao();
        boolean done = validateRegisterUser(userRegisterDto.getConfirmPassword(), userRegisterDto);
        if (userExists(userRegisterDto.getEmail())) {
            throw new UserException(Constants.EMAIL_ALREADY_TAKEN);
        }
        if (!done) {
            throw new UserException(Constants.PAS_NOT_MATCH);
        }
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(BCryptUtil.hashPassword(userRegisterDto.getPassword()));
        user.setEmail(userRegisterDto.getEmail());
        user.setType(userRegisterDto.getType());
        userDao.registerUser(user);
        return user;
    }

    @Override
    public User logUser(UserLoginDto userLoginDto, HttpSession session) throws UserException {
        if (userLoginDto.getEmail() == null || userLoginDto.getEmail().isEmpty()) {
            throw new UserException(Constants.EMPTY_INPUT);
        }
        if (userLoginDto.getPassword() == null || userLoginDto.getPassword().isEmpty()) {
            throw new UserException(Constants.EMPTY_INPUT);
        } else {
            UserDao userDao = new UserDao();
            User user = userDao.getUserByEmail(userLoginDto.getEmail());
            String password = userLoginDto.getPassword();
            if (user == null) {
                throw new UserException(Constants.NOT_EXISTING_USER);
            }
            if (!validateLoginUser(password, user)) {
                throw new UserException(Constants.WRONG_PASSWORD);
            } else {
                session.setAttribute(Constants.LOGGED, user);
            }
            return user;
        }
    }

    @Override
    public void logOutUser(HttpSession session) throws UserException {
        if (getLoggedUser(session) != null) {
            session.setAttribute(Constants.LOGGED, null);
            return;
        }
        throw new UserException(Constants.NOT_LOGGED);
    }

    public static User getLoggedUser(HttpSession session) throws UserException {
        if (session.isNew() || session.getAttribute(Constants.LOGGED) == null) {
            throw new UserException(Constants.NOT_LOGGED);
        }
        User user = (User) session.getAttribute(Constants.LOGGED);
        return user;
    }

    private boolean validateLoginUser(String password, User user) {
        return BCryptUtil.checkPass(password, user.getPassword());
    }

    private boolean validateRegisterUser(String password, UserRegisterDto userDto) {
        boolean done = false;
        if (password != null && password.trim().length() > 0 && password.trim().length() < 20) {
            if (userDto.getPassword().equals(password)) {
                done = true;
            }
        }
        return done;
    }

    private boolean userExists(String email) {
        UserDao userDao = new UserDao();
        User user = userDao.getUserByEmail(email);
        return user != null;
    }

}
