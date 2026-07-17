package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class RequestRegistroDetalleInventario {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idArticulo;
}
