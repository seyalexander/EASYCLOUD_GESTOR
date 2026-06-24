package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroNotaCredito {
    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 250, message = "El motivo no debe superar los 250 caracteres")
    private String motivo;

    @PositiveOrZero(message = "El total no puede ser negativo")
    private double montoADevolver;

}
