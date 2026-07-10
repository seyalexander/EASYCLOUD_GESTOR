package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroDevolucion {
    @Min(value = 1, message = "El id del detalle venta debe ser mayor a 0")
    private long idVenta;

    @Min(value = 1, message = "El id del artículo debe ser mayor a 0")
    private long idArticulo;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private double cantidad;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 250, message = "El motivo no debe superar los 250 caracteres")
    private String motivo;

}
