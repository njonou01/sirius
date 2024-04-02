package edu.ssng.ing1.sirius.client.controllers.authentification;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Rules {
    public static Boolean isValidEmail(String email) {
        String regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[a-z])(?=.*[@#$%^&+=-_*.])(?=\\S+$).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }

    public static Boolean isValidZip(String codePostal) {
        String regex = "\\b\\d{5}\\b";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(codePostal);
        return matcher.matches();
    }

    public static Boolean isValidName(String name) {
        String regex = "^(?=.*[aeiouyAEIOUY])[a-zA-Z]{3,}(?<![bcdfghjklmnpqrstvwxzBCDFGHJKLMNPQRSTVWXZ]{4})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }
    public static Boolean isValidFrenchZip(String zip){
        String regex = "^\\d{5}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        return matcher.matches();
    }
    public static Boolean isValidDate(String zip){
        String regex = "(0[1-9]|1[0-2])/(0[1-9]|[1-2][0-9]|3[0-1])/\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(zip);
        return matcher.matches();
    }

}
