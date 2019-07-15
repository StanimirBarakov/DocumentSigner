package com.axway.academy.util;

import org.mindrot.jbcrypt.BCrypt;

public class BCryptUtil {

    private BCryptUtil() {
    }

    public static String hashPassword(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPass(String password, String hashedPassword) {
        return BCrypt.checkpw(password, hashedPassword);
    }
}