package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroVentaResumenDiario {

    @Min(value = 1, message = "El id de sucursal debe ser mayor a 0")
    private long idSucursal;

    @PositiveOrZero(message = "El monto inicial no puede ser negativo")
    private double montoInical;

    @PositiveOrZero(message = "El total de impuestos no puede ser negativo")
    private double totalImpuestos;

    @PositiveOrZero(message = "El total neto no puede ser negativo")
    private double totalNeto;
}