package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestEditarAllTurnoCaja {

    @Min(value = 1, message = "El id del turno debe ser mayor a 0")
    private long idTurnoCaja;

    @NotNull(message = "La fecha de cierre es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @FutureOrPresent(message = "La fecha de inicio no puede ser una fecha pasada")
    private LocalDateTime fechaCierre;

    @PositiveOrZero(message = "El monto final no puede ser negativo")
    private double montoFinal;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 250, message = "El estado no debe superar los 250 caracteres")
    private String estado;
}