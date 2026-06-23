package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestListaCuentasPorCobrarIDCliente {
    @Schema(
            description = "Estado de las cuentas por cobrar a filtrar",
            example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "PAGADO", "ANULADO", "TODOS"}
    )
    @NotNull(message = "El filtro de estado es obligatorio")
    private FiltroEstadoCuenta estado;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private int idCliente;
}
