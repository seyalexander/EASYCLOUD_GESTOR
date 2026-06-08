package com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaVentaResumenDiario {
    @Schema(
            description = "Estado de las aperturas de caja",
            example = "1",
            allowableValues = {"0: Inactivo", "1: Activo", "2: Todos"}
    )
    @Min(value = 0, message = "El estado mínimo permitido es 0")
    @Max(value = 2, message = "El estado máximo permitido es 2")
    private int estado;

}
