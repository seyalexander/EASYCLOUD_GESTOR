package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.TipoMovimientoKardex;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

@Data
public class RequestRegistroKardexRecortado {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

    @NotNull(message = "El tipo de movimiento es obligatorio")
    private TipoMovimientoKardex tipoMovimiento;

    @PositiveOrZero(message = "La cantidad no puede ser negativa")
    private double cantidad;

    @PositiveOrZero(message = "El costo no puede ser negativo")
    private double costoUnitario;
}
