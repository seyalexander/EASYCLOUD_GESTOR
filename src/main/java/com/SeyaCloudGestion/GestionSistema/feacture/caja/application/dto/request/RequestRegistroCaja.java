package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroCaja {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;
}