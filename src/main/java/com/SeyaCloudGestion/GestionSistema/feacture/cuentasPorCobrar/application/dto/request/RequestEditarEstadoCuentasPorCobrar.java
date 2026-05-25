package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestEditarEstadoCuentasPorCobrar {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idCuentasPorCobrar;

}
