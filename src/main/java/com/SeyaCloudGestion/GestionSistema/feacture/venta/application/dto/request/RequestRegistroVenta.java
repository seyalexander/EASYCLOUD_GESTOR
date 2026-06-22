package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RequestRegistroVenta  {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCliente;
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTurnoCaja;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoMovimiento ;
 /*
    @PositiveOrZero(message = "El sub total no puede ser negativo")
    private double subTotal;

    @PositiveOrZero(message = "El impuesto no puede ser negativo")
    private double impuesto;

    @PositiveOrZero(message = "El total no puede ser negativo")
    private double total;

  */
    @NotEmpty(message = "Debe registrar al menos un detalle de venta")
    private List<RequestRegistroDetalleVenta> detalles;
}