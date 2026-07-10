package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestEditarAllSotck {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idStockArticulo;

    @Positive(message = "El precio debe ser mayor a 0")
    private double stock;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

}