package ru.whitetigersoft.test_application.Model.Utils;

/**
 * Created by denis on 07/07/17.
 */

public class StringHelper {
    public static boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isPasswordValid(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasDigit = false;
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                hasUpperCase = true;
            } else if (Character.isDigit(password.charAt(i))) {
                hasDigit = true;
            } else if (Character.isLowerCase(password.charAt(i)))
                hasLowerCase = true;
        }
        return (password.length() >= 6 && hasLowerCase && hasUpperCase && hasDigit);
    }
}
