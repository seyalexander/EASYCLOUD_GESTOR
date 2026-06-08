package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

import jakarta.validation.constraints.Max;

import io.swagger.v3.oas.annotations.media.Schema;

@Data
public class RequestListaDireccionesClientes {
    @Schema(
            description = "Estado de las direcciones de clientes",
            example = "1",
            allowableValues = {"0: Inactivo", "1: Activo", "2: Todos"}
    )
    @Min(value = 0, message = "El estado mínimo permitido es 0")
    @Max(value = 2, message = "El estado máximo permitido es 2")
    private int estado;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idCliente;

}
