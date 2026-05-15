package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations;

import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class EmpleadoValidator {

    public static void validarEdad(LocalDate fechaNacimiento){

        int edad = Period.between(fechaNacimiento, LocalDate.now()).getYears();

        if(edad < 17){
            throw new IllegalArgumentException("El empleado debe tener al menos 17 años");
        }

        if(edad > 100){
            throw new IllegalArgumentException("Edad inválida");
        }
    }

    public static void validarFechaIngreso(LocalDate fechaIngreso){

        LocalDate hoy = LocalDate.now();

        long dias = ChronoUnit.DAYS.between(fechaIngreso, hoy);

        if (dias > 7) {
            throw new IllegalArgumentException(
                    "La fecha de ingreso no puede ser mayor a 7 días respecto a la fecha actual"
            );
        }

        if (fechaIngreso.isAfter(hoy)) {
            throw new IllegalArgumentException("La fecha de ingreso no puede ser futura");
        }
    }

    public static boolean esNumero(String texto) {
        if (texto == null || texto.isEmpty()) return false;
        return texto.matches("^\\d+$");
    }

    public static boolean esSoloLetras(String texto) {
        if (texto == null || texto.isEmpty()) return false;
        return texto.matches("^[a-zA-Z]+$");
    }

    public static boolean esAlfanumerico(String texto) {
        if (texto == null || texto.isEmpty()) return false;
        return texto.matches("^[a-zA-Z0-9]+$");
    }
}
