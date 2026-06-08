package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllListaPrecios {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idListaPrecios;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 100, message = "La descripción no debe superar los 100 caracteres")
    private String descripcion;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}
