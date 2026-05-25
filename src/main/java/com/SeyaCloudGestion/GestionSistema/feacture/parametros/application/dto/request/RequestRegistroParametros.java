package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroParametros {
    @NotBlank(message = "La clave es obligatoria")
    @Size(max = 250, message = "La clave no debe superar los 250 caracteres")
    private String clave;

    @NotBlank(message = "El valor es obligatorio")
    @Size(max = 500, message = "El valor no debe superar los 500 caracteres")
    private String valor;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 250, message = "La descripción no debe superar los 250 caracteres")
    private String descripcion;
}