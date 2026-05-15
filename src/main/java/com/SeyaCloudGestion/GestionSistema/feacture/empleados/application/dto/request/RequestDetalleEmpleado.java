package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestDetalleEmpleado {

    @Positive
    @NotNull
    private long idEmpleado;
}
