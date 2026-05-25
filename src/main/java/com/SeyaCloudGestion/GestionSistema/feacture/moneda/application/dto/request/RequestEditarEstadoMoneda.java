package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request;

import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class RequestEditarEstadoMoneda {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idMoneda;
    private int estado;
}
