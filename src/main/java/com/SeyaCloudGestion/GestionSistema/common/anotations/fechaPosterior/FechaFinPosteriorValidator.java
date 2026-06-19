package com.SeyaCloudGestion.GestionSistema.common.anotations.fechaPosterior;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapperImpl;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FechaFinPosteriorValidator implements ConstraintValidator<FechaFinPosterior, Object> {

    private String fechaInicioCampo;
    private String fechaFinCampo;

    @Override
    public void initialize(FechaFinPosterior constraintAnnotation) {
        this.fechaInicioCampo = constraintAnnotation.fechaInicio();
        this.fechaFinCampo = constraintAnnotation.fechaFin();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        if (value == null) {
            return true;
        }

        Object fechaInicioValor = new BeanWrapperImpl(value)
                .getPropertyValue(fechaInicioCampo);

        Object fechaFinValor = new BeanWrapperImpl(value)
                .getPropertyValue(fechaFinCampo);


        if (fechaInicioValor == null || fechaFinValor == null) {
            return true;
        }


        boolean valido = false;


        if (fechaInicioValor instanceof LocalDateTime fechaInicio &&
                fechaFinValor instanceof LocalDateTime fechaFin) {

            valido = fechaFin.isAfter(fechaInicio);
        }


        else if (fechaInicioValor instanceof LocalDate fechaInicio &&
                fechaFinValor instanceof LocalDate fechaFin) {

            valido = !fechaFin.isBefore(fechaInicio);
        }


        else {
            return false;
        }


        if (!valido) {
            context.disableDefaultConstraintViolation();

            context.buildConstraintViolationWithTemplate(
                            context.getDefaultConstraintMessageTemplate()
                    )
                    .addPropertyNode(fechaFinCampo)
                    .addConstraintViolation();
        }

        return valido;
    }
}