package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroMovimientoStock {
    @Min(value = 1, message = "El id del artículo debe ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id del almacén debe ser mayor a 0")
    private long idAlmacen;

    @Min(value = 1, message = "El id de tipo movimiento debe ser mayor a 0")
    private long idTipoMovimiento;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private double cantidad;

    @PositiveOrZero(message = "El costo unitario no puede ser negativo")
    private double costoUnitario;

    @Size(max = 250, message = "La observación no debe superar los 250 caracteres")
    private String observacion;

}
