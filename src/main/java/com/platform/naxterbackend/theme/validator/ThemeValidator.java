package com.platform.naxterbackend.theme.validator;

public class ThemeValidator {

    public static Boolean validName(String name) {
        return name != null && !name.isEmpty();
    }
}
