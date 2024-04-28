package it.unipd.dei.dam.awesometournament.utils;

import java.util.regex.Pattern;


/**
 * Provides utility methods for validating input data.
 */
public class Validators {

    /**
     * The regular expression pattern for validating email addresses.
     */
    static String email = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";

    /**
     * The compiled pattern for validating email addresses.
     */
    static Pattern emailPattern;

    /**
     * Static initializer block to compile the email regex pattern.
     */
    static {
        emailPattern = Pattern.compile(email);
    }

    /**
     * Checks if the given string is a valid email address.
     *
     * @param toValidate The string to validate.
     * @return true if the string is a valid email address, false otherwise.
     */
    public static boolean isEmail(String toValidate) {
        return emailPattern.matcher(toValidate).matches();
    }

    /**
     * Checks if the given string represents a valid password.
     *
     * @param toValidate The string to validate.
     * @return true if the string represents a valid password, false otherwise.
     */
    public static boolean isPasswordValid(String toValidate) {
        Pattern uppercase = Pattern.compile("[A-Z]");
        Pattern number = Pattern.compile("[0-9]");

        if(toValidate.length() < 8)
            return false;
        
        if(toValidate.length() > 20)
            return false;

        if(!uppercase.matcher(toValidate).find())
            return false;

        if(!number.matcher(toValidate).find())
            return false;

        return true;
    }
}
