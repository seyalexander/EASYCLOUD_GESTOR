package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllArticulo {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulos;

    @NotBlank(message = "La descripción es obligatoria")
    @Size(max = 200, message = "La descripción no debe superar los 200 caracteres")
    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = false, message = "El precio de venta debe ser mayor a 0")
    private float precioVenta;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;

}
