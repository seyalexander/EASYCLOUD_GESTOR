package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestDetalleEmpleado;

public class ValdiacionRequest_DetalleEmpleados {

    public static void validarDetalleEmpleado(RequestDetalleEmpleado request) {
        if (request.getIdEmpleado() == 0) {
            String mensajeError = "El código del empleado es obligatorio.";
            throw new IllegalArgumentException(mensajeError);
        }

        if (request.getIdEmpleado() < 0) {
            String mensajeError = "Código de empleado no válido.";
            throw new IllegalArgumentException(mensajeError);
        }
    }
}
