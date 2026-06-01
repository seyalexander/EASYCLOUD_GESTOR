package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestEditarEstadoProductoImpuesto {
    @Min(value = 1, message = "El id del impuesto debe ser mayor a 0")
    private long idProductoImpuesto;
}
