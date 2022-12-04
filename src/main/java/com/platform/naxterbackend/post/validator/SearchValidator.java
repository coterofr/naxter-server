package com.platform.naxterbackend.post.validator;

public class SearchValidator {

    public static Boolean validParam(String param) {
        return param != null && !param.isEmpty() && !param.trim().isEmpty();
    }
}
