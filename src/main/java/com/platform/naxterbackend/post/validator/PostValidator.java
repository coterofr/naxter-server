package com.platform.naxterbackend.post.validator;

public class PostValidator {

    public static Boolean validId(String id) {
        return id != null && !id.isEmpty();
    }
}
