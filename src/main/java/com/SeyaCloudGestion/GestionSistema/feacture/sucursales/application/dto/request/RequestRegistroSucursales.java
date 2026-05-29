package com.SeyaCloudGestion.GestionSistema.feacture.sucursales.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroSucursales {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no debe superar los 50 caracteres")
    private String descripcion;

    @Min(value = 1, message = "El id del usuario es obligatorio")
    private long idUsuarioCreacion;
}