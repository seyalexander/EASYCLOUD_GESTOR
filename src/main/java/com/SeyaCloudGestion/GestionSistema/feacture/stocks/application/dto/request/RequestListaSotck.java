package com.SeyaCloudGestion.GestionSistema.feacture.stocks.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaSotck {
    @Schema(
            description = "Listar stock por almancen y producto",
            example = "1",
            allowableValues = {"0: todos"}
    )
    @Min(value = 0, message = "El id mínimo permitido es 0")
    private long idAlmacen;

    @Schema(
            description = "Listar stock por almancen y producto",
            example = "1",
            allowableValues = {"0: todos"}
    )
    @Min(value = 0, message = "El id mínimo permitido es 0")
    private long idProducto;

}
