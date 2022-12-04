package com.platform.naxterbackend.comment.validator;

public class CommentValidator {

    public static Boolean validId(String id) {
        return id != null && !id.isEmpty();
    }
}
