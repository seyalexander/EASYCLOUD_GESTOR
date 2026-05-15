package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestEditarAllEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestRegistroEmpleado;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.response.ResponseDetalleTipoDocumento;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class ValidacionRequest_RegistrarEmpleados {

    public static void validarRegistroEmpleado(
            RequestRegistroEmpleado request,
            ResponseDetalleTipoDocumento responseTipoDocumento
    ) {
        if (request == null) {
            String mensajeError = "No se encontró datos para registrar";
            throw new IllegalArgumentException(mensajeError);
        }

        if (request.getFechaNacimiento() == null || request.getFechaNacimiento().isEmpty()) {
            String mensajeError = "La fecha de nacimiento no puede estar vacía";
            throw new IllegalArgumentException(mensajeError);
        }

        if (request.getIdTipoDocumento() == 0) {
            String mensajeError = "El tipo de documento seleccionado no es válido";
            throw new IllegalArgumentException(mensajeError);
        }

        if (request.getDocumento() == null || request.getDocumento().isEmpty() || request.getDocumento().trim().isEmpty()) {
            String mensajeError = "El N° de documento no puede estar vacío";
            throw new IllegalArgumentException(mensajeError);
        }

        try {
            LocalDate fechaNacimiento = LocalDate.parse(request.getFechaNacimiento());
            EmpleadoValidator.validarEdad(fechaNacimiento);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha nacimiento inválido. Use YYYY-MM-DD");
        }

        try {
            LocalDate fechaIngreso = LocalDate.parse(request.getFechaIngreso());
            EmpleadoValidator.validarFechaIngreso(fechaIngreso);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Formato de fecha ingreso inválido. Use YYYY-MM-DD");
        }

        validarEdicionEmpleado_TipoDocumento(request, responseTipoDocumento);

    }

    private static void validarEdicionEmpleado_TipoDocumento(
            RequestRegistroEmpleado request,
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

}
