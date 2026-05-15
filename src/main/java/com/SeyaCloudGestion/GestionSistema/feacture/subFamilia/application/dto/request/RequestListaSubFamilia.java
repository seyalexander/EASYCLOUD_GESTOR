package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestListaSubFamilia {
    @Schema(
            description = "Estado de la familia",
            example = "1",
            allowableValues = {"0: Inactivo", "1: Activo", "2: Todos"}
    )
    private int estado;
    private long idFamilia;
}
