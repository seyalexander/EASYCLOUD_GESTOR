package com.SeyaCloudGestion.GestionSistema.feacture.serieDocumento.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RequestObtenerCorrelativo {
    @Min(value = 1, message = "El id de tipo comprobante debe ser mayor a 0")
    private long idSerieDocumento;

}
