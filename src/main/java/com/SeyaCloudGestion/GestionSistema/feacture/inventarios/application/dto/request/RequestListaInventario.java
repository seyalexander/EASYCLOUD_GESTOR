package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaInventario {
    @Schema(
            description = "Estado del inventario",
            example = "TODOS",
            allowableValues = {"ACTIVO", "FINALIZADO", "TODOS"}
    )
    private EstadoInventarioRequest estado;

    @Schema(
            description = "ID del almacén",
            example = "1"
    )
    private long idAlmacen;
}
