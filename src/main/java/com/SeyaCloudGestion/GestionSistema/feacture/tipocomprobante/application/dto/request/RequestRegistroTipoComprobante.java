package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request;// Generado a partir de la arquitectura de subFamilia.

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestRegistroTipoComprobante {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 50, message = "La descripción no debe superar los 50 caracteres")
    private String descripcion;

    @NotBlank(message = "El código SUNAT es obligatorio")
    @Size(max = 2, message = "El código SUNAT no debe superar los 2 caracteres")
    private String codigoSunat;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

}
