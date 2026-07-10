package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaTurnoCaja {
    @Schema(
            description = "Estado del turno de caja",
            example = "ABIERTO",
            allowableValues = {"ABIERTO", "CERRADO", "TODOS"}
    )
    private Estado estado;

}
