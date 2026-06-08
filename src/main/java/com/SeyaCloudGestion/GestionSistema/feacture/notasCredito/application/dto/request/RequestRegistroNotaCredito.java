package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;
@Data
public class RequestRegistroNotaCredito {
    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 250, message = "El motivo no debe superar los 250 caracteres")
    private String motivo;

    @NotNull(message = "La fecha de emisión es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaEmision;

    @PositiveOrZero(message = "El total no puede ser negativo")
    private double total;

    @NotNull(message = "La fecha de ingreso es obligatoria")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime fechaIngreso;
}
