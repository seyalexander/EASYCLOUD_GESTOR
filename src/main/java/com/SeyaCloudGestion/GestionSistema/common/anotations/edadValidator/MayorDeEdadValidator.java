package com.SeyaCloudGestion.GestionSistema.common.anotations.edadValidator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.Period;

public class MayorDeEdadValidator implements ConstraintValidator<MayorDeEdad, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        try {
            LocalDate fecha = LocalDate.parse(value);
            return Period.between(fecha, LocalDate.now()).getYears() >= 18;
        } catch (Exception e) {
            return false;
        }
    }
}
