package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestDetalleFamilia {

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private Long idFamilia;
}
