package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestRegistroDetalleTransferencia {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;

    @Positive(message = "La cantidad debe ser mayor a 0")
    private double cantidad;

}
