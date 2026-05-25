package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request;

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
    private LocalDateTime fechaApertura;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaCierre;

    @PositiveOrZero(message = "El monto inicial no puede ser negativo")
    private double montoInicial;

    @PositiveOrZero(message = "El monto final no puede ser negativo")
    private double montoFinal;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 250, message = "El estado no debe superar los 250 caracteres")
    private String estado;
}