package com.platform.naxterbackend.post.validator;

import java.util.List;

public class SearchValidator {

    public static Boolean validParam(String param) {
        return param != null && !param.isEmpty() && !param.trim().isEmpty();
    }

    public static Boolean validParam(List<String> params) {
        if(params != null && !params.isEmpty()) {
            for(String param : params) {
                if(params == null || params.isEmpty() || param.trim().isEmpty()) {
                    return Boolean.FALSE;
                }
            }

            return Boolean.TRUE;
        } else {
            return Boolean.FALSE;
        }
    }
}
