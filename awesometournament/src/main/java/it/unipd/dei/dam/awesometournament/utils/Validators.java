package it.unipd.dei.dam.awesometournament.utils;

import java.util.regex.Pattern;


public class Validators {
    static String email = "^([\\w-]+(?:\\.[\\w-]+)*)@((?:[\\w-]+\\.)*\\w[\\w-]{0,66})\\.([a-z]{2,6}(?:\\.[a-z]{2})?)$";
    static Pattern emailPattern;

    static {
        emailPattern = Pattern.compile(email);
    }

    public static boolean isEmail(String toValidate) {
        return emailPattern.matcher(toValidate).matches();
    }    
}
