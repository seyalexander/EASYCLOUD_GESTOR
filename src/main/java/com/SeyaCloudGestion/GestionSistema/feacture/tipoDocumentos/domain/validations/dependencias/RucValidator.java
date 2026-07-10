package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.domain.validations.dependencias;

public class RucValidator {
    public static boolean esValido(String ruc) {
        return ruc.matches("\\d{11}") && (ruc.startsWith("10") || ruc.startsWith("15") || ruc.startsWith("17") || ruc.startsWith("20"));
    }
    public static boolean esPersonaNatural(String ruc) {
        if (!esValido(ruc)) {
            return false;
        }
        return ruc.startsWith("10") || ruc.startsWith("15") || ruc.startsWith("17");
    }

    public static boolean esPersonaJuridica(String ruc) {
        if (!esValido(ruc)) {
            return false;
        }
        return ruc.startsWith("20");
    }
}
