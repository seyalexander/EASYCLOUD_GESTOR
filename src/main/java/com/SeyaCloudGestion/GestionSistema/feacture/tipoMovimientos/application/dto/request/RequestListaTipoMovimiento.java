package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RequestListaTipoMovimiento {
    @Schema(
            description = "Estado de las subfamilias",
            example = "1",
            allowableValues = {"0: Inactivo", "1: Activo", "2: Todos"}
    )
    @Min(value = 0, message = "El estado mínimo permitido es 0")
    @Max(value = 2, message = "El estado máximo permitido es 2")
    private int estado;
}
