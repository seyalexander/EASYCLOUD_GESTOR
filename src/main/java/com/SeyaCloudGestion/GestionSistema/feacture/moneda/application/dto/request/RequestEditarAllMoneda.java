package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllMoneda {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idMoneda;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 255, message = "La descripción no debe superar los 255 caracteres")
    private String descripcion;

    @NotBlank(message = "El símbolo es obligatorio")
    @Size(max = 10, message = "El símbolo no debe superar los 10 caracteres")
    private String simbolo;

    @Min(value = 0, message = "El valor de esPrincipal no puede ser negativo")
    @Max(value = 1, message = "El valor de esPrincipal solo puede ser 0 o 1")
    private int esPrincipal;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

}
