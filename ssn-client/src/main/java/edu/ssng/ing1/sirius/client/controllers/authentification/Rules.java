package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rules {
    public static Boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isValidPassword(String password) {
        if (password == null) {
            return false;
        }
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#.$%^&*()-+]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static boolean checkNoSpace(String word) {
        String regex = "\\S+";
        return word.matches(regex);
    }

    public static boolean verifyLength(String password) {
        if (password == null)
            return false;

        return password.length() >= 8;
    }

    public static boolean verifyUppercase(String password) {
        return !password.equals(password.toLowerCase());
    }

    public static boolean verifyLowercase(String password) {
        return !password.equals(password.toUpperCase());
    }

    public static boolean verifySpecial(String password) {
        String regex = ".*[@#$\\-\\.%^&+=!].*";
        return password.matches(regex);
    }

    public static Boolean isValidZip(String codePostal) {
        if (codePostal == null) {
            return false;
        }
        String regex = "\\b\\d{5}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codePostal);
        return matcher.matches();
    }

    public static Boolean isValidName(String name) {
        if (name == null) {
            return false;
        }
        String regex = "^(?=.*[aeiouyAEIOUY])[a-zA-Z]{3,}(?<![bcdfghjklmnpqrstvwxzBCDFGHJKLMNPQRSTVWXZ]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public static Boolean isValidFrenchZip(String zip) {
        if (zip == null) {
            return false;
        }
        String regex = "^\\d{5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        return matcher.matches();
    }

    public static Boolean isValidDate(String zip) {
        if (zip == null) {
            return false;
        }
        String regex = "(0?[1-9]|[12][0-9]|3[01])/(0?[1-9]|1[0-2])/\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        return (matcher.matches() || isValidDate2(zip));
    }
    private static Boolean isValidDate2(String zip) {
        if (zip == null) {
            return false;
        }
        String regex = "\\d{4}-(0?[1-9]|1[0-2])-(0?[1-9]|[12][0-9]|3[01])";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        return matcher.matches();
    }

}
