package com.SeyaCloudGestion.GestionSistema.common.anotations.fechaPosterior;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FechaFinPosteriorValidator.class)
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface FechaFinPosterior {

    String message() default "La fecha fin debe ser posterior a la fecha inicio";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String fechaInicio();

    String fechaFin();
}