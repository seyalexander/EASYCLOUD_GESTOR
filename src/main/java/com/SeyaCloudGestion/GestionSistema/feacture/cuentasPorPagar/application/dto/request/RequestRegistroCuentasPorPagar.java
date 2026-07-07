package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroCuentasPorPagar {

    @Min(value = 1, message = "El id de compra debe ser mayor a 0")
    private long idCompra;

    @PositiveOrZero(message = "El monto pendiente no puede ser negativo")
    private double montoPendiente;

    @NotNull(message = "La fecha de vencimiento es obligatoria")
    @FutureOrPresent(message = "La fecha de vencimiento no puede ser menor a hoy")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaVencimiento;

}