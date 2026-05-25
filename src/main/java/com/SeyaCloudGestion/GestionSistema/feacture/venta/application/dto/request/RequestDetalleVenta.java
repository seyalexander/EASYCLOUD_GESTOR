package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleVenta {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idVenta;

}
