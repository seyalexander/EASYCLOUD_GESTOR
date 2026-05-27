package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class RequestEditarEstadoMoneda {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idMoneda;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}
