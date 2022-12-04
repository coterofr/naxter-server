package com.platform.naxterbackend.user.validator;

public class UserValidator {

    public static Boolean validName(String name) {
        return name != null && !name.isEmpty();
    }
}
