package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleVentaResumenDiario {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idVentaResumenDiario;

}
