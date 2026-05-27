package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestEditarAllTipoDocumento {
    @Min(value = 1, message = "El id no puede ser negativo")
    private long idTipoDocumento;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String descripcion;

    @Min(value = 1, message = "La longitud minima no puede ser negativa")
    private int longitudMin;

    @Max(value = 20, message = "El estado máximo permitido es 20")
    private int longitudMax;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 2, message = "La descripción no debe superar los 2 caracteres")
    private String codigoSunat;

    @Min(value = 1, message = "El tipo de caracter debe ser mayor a 0")
    private int tipoCaracter;
}
