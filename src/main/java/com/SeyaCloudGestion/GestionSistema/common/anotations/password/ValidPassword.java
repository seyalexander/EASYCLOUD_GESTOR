package com.SeyaCloudGestion.GestionSistema.common.anotations.password;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = PasswordValidator.class)
@Target({ FIELD })
@Retention(RUNTIME)
public @interface ValidPassword {
    String message() default "la contraseña debe tener mínimo 8 caracteres, una mayúscula, una minúscula , un número y un caracter especial";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}