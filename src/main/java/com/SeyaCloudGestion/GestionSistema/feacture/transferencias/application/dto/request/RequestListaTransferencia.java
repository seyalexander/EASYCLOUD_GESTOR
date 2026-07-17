package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.Estado;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class RequestListaTransferencia {
    @Schema(
            description = "Estado de la transferencia entre almacenes",
            example = "PENDIENTE",
            allowableValues = {"PENDIENTE", "FINALIZADO", "TODOS"}
    )
    private EstadoTransferenciaRequest estado;
}
