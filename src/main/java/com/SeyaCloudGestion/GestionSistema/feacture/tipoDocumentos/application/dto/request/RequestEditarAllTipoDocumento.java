package com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoDocumentos.infraestructure.persistence.model.TipoCaracter;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllTipoDocumento {
    @Min(value = 1, message = "El id no puede ser negativo")
    private long idTipoDocumento;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no debe superar los 50 caracteres")
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

    @NotNull(message = "El tipo de carácter es obligatorio")
    private TipoCaracter tipoCaracter;

}
