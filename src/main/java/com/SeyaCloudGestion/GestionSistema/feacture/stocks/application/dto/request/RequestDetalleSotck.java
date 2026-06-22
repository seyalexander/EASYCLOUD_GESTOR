package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleSotck {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idProducto;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idAlmacen;

}
