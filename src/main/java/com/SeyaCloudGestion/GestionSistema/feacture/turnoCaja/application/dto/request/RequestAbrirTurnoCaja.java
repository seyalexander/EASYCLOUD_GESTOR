package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestAbrirTurnoCaja {
    /*
    @Min(value = 1, message = "El id de sucursal debe ser mayor a 0")
    private long idSucursal;
    */
    @PositiveOrZero(message = "El monto inicial no puede ser negativo")
    private double montoInicial;

}