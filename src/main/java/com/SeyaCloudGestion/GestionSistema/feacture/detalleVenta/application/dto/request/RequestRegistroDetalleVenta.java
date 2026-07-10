package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestRegistroDetalleVenta{

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private double cantidad;

    @Positive(message = "El precio Unitario debe ser mayor a 0")
    private double precioUnitario;

    @Positive(message = "El descuento debe ser mayor a 0")
    private double descuento;

}
