package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RequestRegistroProductoImpuesto {
    @Min(value = 1, message = "El id del artículo debe ser mayor a 0")
    private long idArticulo;

    @DecimalMin(value = "0.00", message = "El porcentaje no puede ser negativo")
    @DecimalMax(value = "100.00", message = "El porcentaje no debe superar el 100")
    private double porcentaje;

}
