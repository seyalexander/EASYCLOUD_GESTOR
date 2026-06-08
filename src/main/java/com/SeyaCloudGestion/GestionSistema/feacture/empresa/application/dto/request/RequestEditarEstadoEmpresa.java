package com.SeyaCloudGestion.GestionSistema.feacture.empresa.application.dto.request;

import jakarta.validation.constraints.Min;

import lombok.Data;

@Data
public class RequestEditarEstadoEmpresa {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idEmpresa;
}
