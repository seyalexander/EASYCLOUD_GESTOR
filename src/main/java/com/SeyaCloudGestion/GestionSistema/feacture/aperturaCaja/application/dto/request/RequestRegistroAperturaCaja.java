package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroAperturaCaja {
    @Min(value = 1, message = "El id de sucursal debe ser mayor a 0")
    private long idSucursal;

    @Min(value = 1, message = "El id de usuario debe ser mayor a 0")
    private long idUsuario;

    @PositiveOrZero(message = "El monto inicial no puede ser negativo")
    private double montoInical;
}