package com.SeyaCloudGestion.GestionSistema.common.anotations.letras;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = SoloLetrasValidator.class)
@Target({FIELD})
@Retention(RUNTIME)
public @interface SoloLetras {

    String message() default "Solo se permiten letras y espacios";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}