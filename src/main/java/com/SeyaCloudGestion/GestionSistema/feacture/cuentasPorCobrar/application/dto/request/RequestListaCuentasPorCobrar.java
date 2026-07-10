package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaCuentasPorCobrar {
    @Schema(
            description = "Estado de las cuentas por cobrar a filtrar",
            example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "PAGADO", "ANULADO", "TODOS"}
    )
    @NotNull(message = "El filtro de estado es obligatorio")
    private FiltroEstadoCuenta estado;

}
