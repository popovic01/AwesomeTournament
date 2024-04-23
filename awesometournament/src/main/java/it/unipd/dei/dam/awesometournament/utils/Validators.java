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
