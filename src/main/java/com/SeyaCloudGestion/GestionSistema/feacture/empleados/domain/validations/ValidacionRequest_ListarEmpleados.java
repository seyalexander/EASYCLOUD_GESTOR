package com.SeyaCloudGestion.GestionSistema.feacture.empleados.domain.validations;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request.RequestListaEmpleado;

public class ValidacionRequest_ListarEmpleados {
    public static void validarListarEmpleado(RequestListaEmpleado request) {
        if (request.getEstado()< 0 || request.getEstado() > 2) {
            throw new IllegalArgumentException("El valor del estado no es el correcto.");
        }
    }
}
