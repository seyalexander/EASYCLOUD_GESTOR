package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class RequestRegistroPago  {

    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;

    @Min(value = 1, message = "El id de tipo pago debe ser mayor a 0")
    private long idTipoPago;

    @PositiveOrZero(message = "El monto no puede ser negativo")
    private double monto;

    @NotBlank(message = "La referencia es obligatoria")
    private String referencia;
}
