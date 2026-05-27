package com.SeyaCloudGestion.GestionSistema.common.anotations.NumeroTelefonicoValidator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = ValidarTelefonoValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidarTelefono {

    String message() default "El telefono debe ser un numero valido de 9 dígitos y comenzar con 9";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}