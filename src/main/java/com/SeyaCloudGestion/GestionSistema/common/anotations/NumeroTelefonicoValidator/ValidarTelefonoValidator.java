package com.SeyaCloudGestion.GestionSistema.common.anotations.NumeroTelefonicoValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ValidarTelefonoValidator implements ConstraintValidator<ValidarTelefono, String> {
    @Override
    public boolean isValid (String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        return value.matches("^9\\d{8}$");
    }
}
