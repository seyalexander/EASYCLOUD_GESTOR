package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaAjuste {
    @Schema(
            description = "Identificador del artículo. Use 0 para consultar todos los artículos.",
            example = "0",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Min(value = 0, message = "El id del artículo no puede ser menor a 0")
    private long idArticulo;

    @Schema(
            description = "Identificador del almacén. Use 0 para consultar todos los almacenes.",
            example = "0",
            minimum = "0",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    @Min(value = 0, message = "El id del almacén no puede ser menor a 0")
    private long idAlmacen;

}
