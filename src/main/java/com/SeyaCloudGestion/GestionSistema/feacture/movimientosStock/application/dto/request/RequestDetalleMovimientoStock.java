package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestDetalleMovimientoStock {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idDetalleMovimiento;
}
