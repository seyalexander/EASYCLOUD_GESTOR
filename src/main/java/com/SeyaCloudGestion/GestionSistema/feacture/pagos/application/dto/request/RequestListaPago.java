package com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestListaPago {
    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;
}
