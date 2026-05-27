package com.SeyaCloudGestion.GestionSistema.common.anotations.noFechaFutura;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = NoFechaFuturaValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface NoFechaFutura {

    String message() default "La fecha de ingreso no puede ser mayor a la fecha actual";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}