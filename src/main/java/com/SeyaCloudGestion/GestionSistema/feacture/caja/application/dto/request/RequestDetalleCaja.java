package com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleCaja {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCaja;

}
