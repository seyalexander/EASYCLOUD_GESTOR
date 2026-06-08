package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestEditarAllSubFamilia {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idFamilia;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idSubFamilia;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String subFamiliaDescripcion;

    @Size(max = 500, message = "La URL de la imagen es demasiado larga")
    private String imagenUrl;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}
