package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;


@Data
public class RequestRegistroDetallePago {

    @Min(value = 1, message = "El id de tipo pago debe ser mayor a 0")
    private long idTipoPago;

    @PositiveOrZero(message = "El monto no puede ser negativo")
    private double monto;

    @NotBlank(message = "La referencia es obligatoria")
    private String referencia;
}
