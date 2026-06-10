package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestEditarAllProductoImpuesto {
    @Min(value = 1, message = "El id del impuesto debe ser mayor a 0")
    private long idProductoImpuesto;

    @DecimalMin(value = "0.00", message = "El porcentaje no puede ser negativo")
    @DecimalMax(value = "100.00", message = "El porcentaje no debe superar el 100")
    private double porcentaje;

    @Min(value = 0, message = "El estado no puede ser negativo")
    @Max(value = 1, message = "El estado solo puede ser 0 o 1")
    private int estado;
}
