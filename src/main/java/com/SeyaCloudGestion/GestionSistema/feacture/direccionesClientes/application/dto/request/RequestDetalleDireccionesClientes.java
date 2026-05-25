package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleDireccionesClientes {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idDireccionesClientes;

}
