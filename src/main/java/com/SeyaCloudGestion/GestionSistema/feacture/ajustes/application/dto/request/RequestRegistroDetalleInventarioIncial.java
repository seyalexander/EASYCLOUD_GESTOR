package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request;

import jakarta.validation.constraints.Positive;
import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestRegistroDetalleInventarioIncial {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private double cantidad;

    @Positive(message = "El costo Unitario debe ser mayor a 0")
    private double costoUnitario;
}
