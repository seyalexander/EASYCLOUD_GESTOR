package com.SeyaCloudGestion.GestionSistema.feacture.dashboard.application.dto.request;

import lombok.Data;

import jakarta.validation.constraints.Min;

@Data
public class RequestDetalleDashboard {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idDashboard;

}
