package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestRegistroSotck {



    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idProducto;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

    @Positive(message = "El precio debe ser mayor a 0")
    private double stock;
}