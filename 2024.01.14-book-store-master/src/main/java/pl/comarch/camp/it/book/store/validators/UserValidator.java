package pl.comarch.camp.it.book.store.validators;

import pl.comarch.camp.it.book.store.exceptions.UserValidationException;
import pl.comarch.camp.it.book.store.model.dto.RegisterUserDTO;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserValidator {
    public static void validateLogin(String login) {
        String regex = "^[a-z0-9]{5,}$";
        if(!login.matches(regex)) {
            throw new UserValidationException("Login too short");
        }
    }

    public static void validateName(String name) {
        String regex = "^[A-Z][a-z]{2,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(name);
        if(!matcher.matches()) {
            throw new UserValidationException("Name incorrect");
        }
    }

    public static void validateSurname(String surname) {
        String regex = "^[A-Z][a-z]+([-\\ ][A-Z][a-z]+)?$";
        if(!surname.matches(regex)) {
            throw new UserValidationException("Surname incorrect");
        }
    }

    public static void validatePassword(String password) {
        String regex = "^([\\w]{4,}\\d[\\w]*)?([\\w]{3,}\\d[\\w]+)?([\\w]{2,}\\d[\\w]{2,})?([\\w]+\\d[\\w]{3,})?([\\w]*\\d[\\w]{4,})?$";
        if(!password.matches(regex)) {
            throw new UserValidationException("Password too short");
        }
    }

    public static void validatePasswordsEquality(String pass1, String pass2) {
        if(!pass1.equals(pass2)) {
            throw new UserValidationException("Passwords not equal");
        }
    }

    public static void validateUserDto(RegisterUserDTO userDTO) {
        UserValidator.validateName(userDTO.getName());
        UserValidator.validateSurname(userDTO.getSurname());
        UserValidator.validateLogin(userDTO.getLogin());
        UserValidator.validatePassword(userDTO.getPassword());
        UserValidator.validatePasswordsEquality(userDTO.getPassword(), userDTO.getPassword2());
    }
}
