package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestEditarAllCuentasPorCobrar {
    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idCuentaPorCobrar;

    @PositiveOrZero(message = "El monto pendiente no puede ser negativo")
    private double montoPendiente;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    @FutureOrPresent(message = "La fecha de vencimiento no puede ser una fecha pasada")
    private LocalDateTime fechaVencimiento;

    @NotBlank(message = "El estado es obligatorio")
    @Size(max = 20, message = "El estado no debe superar los 20 caracteres")
    private String estado;
}