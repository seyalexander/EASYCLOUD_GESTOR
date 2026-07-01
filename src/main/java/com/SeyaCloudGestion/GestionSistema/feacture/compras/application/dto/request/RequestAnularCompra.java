package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestAnularCompra {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCompra;
}
