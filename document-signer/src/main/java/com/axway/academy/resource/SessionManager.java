package com.axway.academy.resource;


import com.axway.academy.model.entity.User;
import com.axway.academy.model.exception.UserException;
import com.axway.academy.util.Constants;

import javax.servlet.http.HttpSession;

public class SessionManager {

    public static User getLoggedUser(HttpSession session) throws UserException {
        if(session.isNew() || session.getAttribute(Constants.LOGGED) == null) {
            throw new UserException(Constants.NOT_LOGGED);
        }
        return (User) session.getAttribute(Constants.LOGGED);
    }

}