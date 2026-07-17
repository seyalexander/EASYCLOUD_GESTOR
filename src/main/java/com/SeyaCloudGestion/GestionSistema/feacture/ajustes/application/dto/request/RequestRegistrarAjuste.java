package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistrarAjuste {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

    @PositiveOrZero(message = "La cantidad debe ser mayor o igual a 0")
    private double cantidad;

    @NotBlank(message = "El motivo es obligatorio")
    @Size(max = 250, message = "El motivo no debe superar los 250 caracteres")
    private String motivo;
}
