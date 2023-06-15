package org.example.core;

import java.util.regex.Pattern;

public class CommonPageMethods {
    public CommonPageMethods() {
    }

    /**
     * Regular Expression to Check Characters in the Top-Level Domain
     */
    public static boolean emailPatternMatches(String emailAddress) {
        String regexPattern = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
                + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,4}$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    /**
     * Regular Expression to Check Characters in the Password. The Password
     * has a minimum length of 8 characters
     * and contains at least three out of four character classes: lower case letters,
     * upper case letters, digits, and special non-alphanumeric characters.
     */

    public static boolean passwordPatternMatches(String password) {
        String regexPattern = "^(?:(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)|(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)|(?=.*[a-z])(?=.*\\d)(?=.*\\W)|(?=.*[A-Z])(?=.*\\d)(?=.*\\W)).{8,}$";
        return Pattern.compile(regexPattern)
                .matcher(password)
                .matches();
    }

}
