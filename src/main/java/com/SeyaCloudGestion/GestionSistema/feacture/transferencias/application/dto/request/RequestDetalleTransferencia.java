package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestDetalleTransferencia {
    @Min(value = 1, message = "El id de la transferencia debe ser mayor a 0")
    private long idTransferencia;
}
