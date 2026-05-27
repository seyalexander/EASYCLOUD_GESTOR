package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroMarca {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;

    @Size(max = 500, message = "La URL de la imagen no debe superar los 500 caracteres")
    private String imagenUrl;
}
