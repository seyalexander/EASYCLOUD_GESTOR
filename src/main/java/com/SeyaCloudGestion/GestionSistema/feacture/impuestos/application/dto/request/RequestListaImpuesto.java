package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestListaImpuesto {
    @Schema(
            description = "Estado del impuesto",
            example = "1",
            allowableValues = {"0: Inactivo", "1: Activo", "2: Todos"}
    )
    private int estado;
}
