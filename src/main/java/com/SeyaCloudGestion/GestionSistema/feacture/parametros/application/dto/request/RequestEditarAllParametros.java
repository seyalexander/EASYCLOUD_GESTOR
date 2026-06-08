package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllParametros {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idParametros;

    @NotBlank(message = "La clave es obligatoria")
    @Size(max = 100, message = "La clave no debe superar los 100 caracteres")
    private String clave;

    @NotBlank(message = "El valor es obligatorio")
    @Size(max = 500, message = "El valor no debe superar los 500 caracteres")
    private String valor;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no debe superar los 200 caracteres")
    private String descripcion;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}