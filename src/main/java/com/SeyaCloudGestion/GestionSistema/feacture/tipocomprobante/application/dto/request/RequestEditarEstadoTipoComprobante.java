package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request;// Generado a partir de la arquitectura de subFamilia.

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestEditarEstadoTipoComprobante {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idTipoComprobante;
}
