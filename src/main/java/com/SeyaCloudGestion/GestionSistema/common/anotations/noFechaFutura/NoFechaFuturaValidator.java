package com.SeyaCloudGestion.GestionSistema.common.anotations.noFechaFutura;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class NoFechaFuturaValidator implements ConstraintValidator<NoFechaFutura, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        try {
            LocalDate fecha = LocalDate.parse(value);
            return !fecha.isAfter(LocalDate.now());
        } catch (Exception e) {
            return false;
        }
    }
}