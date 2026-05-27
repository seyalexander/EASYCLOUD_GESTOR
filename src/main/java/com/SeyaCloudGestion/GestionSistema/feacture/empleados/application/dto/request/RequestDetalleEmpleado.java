package com.SeyaCloudGestion.GestionSistema.feacture.empleados.application.dto.request;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestDetalleEmpleado {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idEmpleado;

}
