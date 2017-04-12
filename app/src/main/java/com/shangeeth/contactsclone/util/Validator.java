package com.shangeeth.contactsclone.util;

import java.util.regex.Pattern;

/**
 * Created by user on 12/04/17.
 */

public class Validator {
    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    private static final String MOBILE_NUMBER_PATTERN ="^([+][9][1]|[9][1]|[0]){0,1}([7-9]{1})([0-9]{9})$";

    public static boolean validateMobileNumber(String pNumber){
        return pNumber.matches(MOBILE_NUMBER_PATTERN);
    }
    public static boolean validateEmailAddress(String pEmailId){
        return pEmailId.matches(EMAIL_PATTERN);
    }
}
