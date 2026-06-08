package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleTipoClientes {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idTipoClientes;

}
