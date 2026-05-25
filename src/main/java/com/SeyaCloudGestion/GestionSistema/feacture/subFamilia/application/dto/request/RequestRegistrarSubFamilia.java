package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestRegistrarSubFamilia {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idFamilia;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String subFamiliaDescripcion;

    @Size(max = 500, message = "La URL de la imagen es demasiado larga")
    private String imagenUrl;
}
