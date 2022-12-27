package com.platform.naxterbackend.merchandising.validator;

public class MerchandisingValidator {

    public static Boolean validId(String id) {
        return id != null && !id.isEmpty();
    }
}
