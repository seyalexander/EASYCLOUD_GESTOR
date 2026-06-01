package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.common.anotations.fechaPosterior.FechaFinPosterior;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@FechaFinPosterior(
        fechaInicio = "fechaInicio",
        fechaFin = "fechaFin",
        message = "La fecha fin debe ser posterior a la fecha inicio"
)
public class RequestEditarAllProductoPrecio {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idProductoPrecio;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idListaPrecio;

    @Positive(message = "El precio debe ser mayor a 0")
    private double precio;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio no puede ser una fecha pasada")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaInicio;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaFin;
}
