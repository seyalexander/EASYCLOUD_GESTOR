package com.SeyaCloudGestion.GestionSistema.common.anotations.letras;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class SoloLetrasValidator implements ConstraintValidator<SoloLetras, String> {

    private static final String REGEX =
            "^[a-zA-ZáéíóúÁÉÍÓÚñÑüÜ\\s]+$";

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        if (value == null || value.isBlank()) {
            return true;
        }

        return value.matches(REGEX);
    }
}