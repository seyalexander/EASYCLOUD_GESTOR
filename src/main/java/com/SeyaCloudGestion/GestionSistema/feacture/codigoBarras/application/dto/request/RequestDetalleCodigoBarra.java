package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestDetalleCodigoBarra {
    @Min(value = 1, message = "El id del codigo debe ser mayor a 0")
    private long idCodigoBarra;
}
