package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestEditarEstadoTipoPagos {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idTipoPagos;

}
