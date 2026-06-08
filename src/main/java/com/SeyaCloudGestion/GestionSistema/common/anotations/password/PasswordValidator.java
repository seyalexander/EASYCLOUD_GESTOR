package com.SeyaCloudGestion.GestionSistema.common.anotations.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<ValidPassword, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        return value.length() >= 8 &&
                value.matches(".*[A-Z].*") &&
                value.matches(".*[a-z].*") &&
                value.matches(".*\\d.*") &&
                value.matches(".*[@#$%^&+=!].*");
    }
}