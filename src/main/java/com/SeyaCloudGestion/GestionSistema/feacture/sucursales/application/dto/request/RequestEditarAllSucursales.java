package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllSucursales {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idSucursales;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no debe superar los 50 caracteres")
    private String descripcion;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

    @Min(value = 1, message = "El id del usuario es obligatorio")
    private long idUsuarioEdicion;
}