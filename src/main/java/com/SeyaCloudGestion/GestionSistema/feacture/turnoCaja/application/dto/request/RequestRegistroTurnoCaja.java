package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.fechaPosterior.FechaFinPosterior;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroTurnoCaja {
    @Min(value = 1, message = "El id de usuario debe ser mayor a 0")
    private long idUsuario;

    @Min(value = 1, message = "El id de sucursal debe ser mayor a 0")
    private long idSucursal;

    @NotNull(message = "La fecha de apertura es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @FutureOrPresent(message = "La fecha de inicio no puede ser una fecha pasada")
    private LocalDateTime fechaApertura;

    @PositiveOrZero(message = "El monto inicial no puede ser negativo")
    private double montoInicial;

}