package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestRegistroFamilia {

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String descripcion;

    @Size(max = 500, message = "La URL de la imagen es demasiado larga")
    private String imagenUrl;
}
