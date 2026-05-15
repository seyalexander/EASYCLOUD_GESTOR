package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidacionRequest_EditarEmpleados {

    public static void validarEdicionEmpleado(
            RequestEditarAllEmpleado request,
            ResponseDetalleTipoDocumento responseTipoDocumento
    ) {
        validarEdicionEmpleado_TipoDocumento(request,responseTipoDocumento);
        validarEdicionEmpleado_Documento(request,responseTipoDocumento);
        validarEdicionEmpleado_FechaNacimiento(request);
        validarEdicionEmpleado_FechaIngreso(request);
        validarEdicionEmpleado_Estado(request.getEstado());
    }

    private static void validarEdicionEmpleado_TipoDocumento(
            RequestEditarAllEmpleado request,
            ResponseDetalleTipoDocumento responseTipoDocumento
    ) {
        if (responseTipoDocumento.getTipoDocumento() == null) {
            throw new IllegalArgumentException("Error al verificar el tipo de documento ingresado");
        }

        if (responseTipoDocumento.getTipoDocumento().getDescripcion() == null || responseTipoDocumento.getTipoDocumento().getDescripcion().trim().isEmpty()) {
            throw new IllegalArgumentException("La descripción del tipo de documento no puede estar vacía; gestiona esto en el módulo de tipo de documento");
        }

        if (responseTipoDocumento.getTipoDocumento().getLongitudMin() == 0 || responseTipoDocumento.getTipoDocumento().getLongitudMax() == 0) {
            throw new IllegalArgumentException("Verificar las longitudes registradas del tipo de documento " + responseTipoDocumento.getTipoDocumento().getDescripcion());
        }

        if (responseTipoDocumento.getTipoDocumento().getLongitudMax() < responseTipoDocumento.getTipoDocumento().getLongitudMin()) {
            throw new IllegalArgumentException("Verificar las longitudes registradas del tipo de documento " + responseTipoDocumento.getTipoDocumento().getDescripcion());
        }
    }

    private static void validarEdicionEmpleado_Documento(
            RequestEditarAllEmpleado request,
            ResponseDetalleTipoDocumento responseTipoDocumento
    ) {
        int longMin = responseTipoDocumento.getTipoDocumento().getLongitudMin();
        int longMax = responseTipoDocumento.getTipoDocumento().getLongitudMax();
        int diferenciaLong = longMax - longMin;

        if (diferenciaLong != 0 && (request.getDocumento().trim().length() < longMin || request.getDocumento().trim().length() > longMax)) {
            throw new IllegalArgumentException("El N° de documento debe estar entre " + longMin + " y " + longMax + " caracteres.");
        }

        if (diferenciaLong == 0 && (request.getDocumento().trim().length() != longMax)) {
            throw new IllegalArgumentException("El N° de documento debe tener " + longMax + " caracteres.");
        }

        // VALIDACIÓN TIPO CARÁCTER
        switch (responseTipoDocumento.getTipoDocumento().getTipoCaracter()) {
            case 1 -> {
                if (!EmpleadoValidator.esNumero(request.getDocumento().trim())) {
                    throw new IllegalArgumentException("El documento debe contener solo números.");
                }
            }
            case 2 -> {
                if (!EmpleadoValidator.esSoloLetras(request.getDocumento().trim())) {
                    throw new IllegalArgumentException("El documento debe contener solo letras.");
                }
            }
            case 3 -> {
                if (!EmpleadoValidator.esAlfanumerico(request.getDocumento().trim())) {
                    throw new IllegalArgumentException("El documento puede contener solo números y letras.");
                }
            }
            default -> throw new IllegalArgumentException("Tipo de carácter no válido.");
        }
    }

    private static void validarEdicionEmpleado_FechaIngreso(RequestEditarAllEmpleado request) {
        if (request.getFechaIngreso() == null || request.getFechaIngreso().isEmpty()) {
            throw new IllegalArgumentException("La fecha de ingreso no puede estar vacía");
        }

        try {
            LocalDate fechaIngreso = LocalDate.parse(request.getFechaIngreso());
            EmpleadoValidator.validarFechaIngreso(fechaIngreso);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha ingreso inválido. Use YYYY-MM-DD");
        }
    }

    private static void validarEdicionEmpleado_FechaNacimiento(RequestEditarAllEmpleado request) {
        if (request.getFechaNacimiento() == null || request.getFechaNacimiento().isEmpty()) {
            throw new IllegalArgumentException("La fecha de nacimiento no puede estar vacía");
        }

        try {
            LocalDate fechaNacimiento = LocalDate.parse(request.getFechaNacimiento());
            EmpleadoValidator.validarEdad(fechaNacimiento);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha nacimiento inválido. Use YYYY-MM-DD");
        }
    }

    private static void validarEdicionEmpleado_NombreCompleto(RequestEditarAllEmpleado request) {
        if (!EmpleadoValidator.esSoloLetras(request.getNombre())) {
            throw new IllegalArgumentException("El nombre debe contener solo letras.");
        }

        if (!EmpleadoValidator.esSoloLetras(request.getApellido())) {
            throw new IllegalArgumentException("El apellido debe contener solo letras.");
        }
    }

    private static void validarEdicionEmpleado_Estado(int estado) {
        if (estado< 0 || estado > 1) {
            throw new IllegalArgumentException("El valor del estado no es el correcto.");
        }
    }
}
