package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestRegistroImpuesto {
    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 250, message = "La descripción no debe superar los 250 caracteres")
    private String descripcion;

    @DecimalMin(value = "0.00", message = "El porcentaje no puede ser negativo")
    @DecimalMax(value = "100.00", message = "El porcentaje no debe superar el 100")
    private double porcentaje;

    @Min(value = 0, message = "El valor mínimo permitido para principal es 0")
    @Max(value = 1, message = "El valor máximo permitido para principal es 1")
    private int esPrincipal;
}
