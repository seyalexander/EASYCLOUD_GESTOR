package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaSerieCaja {
    @Min(value = 0, message = "El id no puede ser negativo")
    private long idCaja;
}
