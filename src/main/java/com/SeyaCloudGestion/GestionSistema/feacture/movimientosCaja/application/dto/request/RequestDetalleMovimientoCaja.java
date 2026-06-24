package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleMovimientoCaja {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idMovimientoCaja;

    @Min(value = 1, message = "El id del turno caja de caja debe ser mayor a 0")
    private long idTurnoCaja;
}
