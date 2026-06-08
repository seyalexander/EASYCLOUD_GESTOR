package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroUnidadMedida {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String descripcion;

    @NotBlank(message = "Las siglas son obligatorias")
    @Size(max = 20, message = "Las siglas no deben superar los 20 caracteres")
    private String siglas;

}
