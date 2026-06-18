package com.SeyaCloudGestion.GestionSistema.feacture.movimientosStock.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestListaMovimientoStock {
    @Schema(
            description = "Listar los movimientos",
            example = "1",
            allowableValues = {"0: todos", }
    )
    @Min(value = 0, message = "El id debe ser positivo")
    private long idArticulo;

    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

}
