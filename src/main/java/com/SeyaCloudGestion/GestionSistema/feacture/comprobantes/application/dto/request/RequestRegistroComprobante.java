package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model.EstadoComprobante;
import jakarta.validation.constraints.*;
import lombok.Data;


@Data
public class RequestRegistroComprobante {
    @Min(value = 1, message = "El id de venta debe ser mayor a 0")
    private long idVenta;

    @Min(value = 1, message = "El id de tipo documento debe ser mayor a 0")
    private long idTipoComprobante;

    @Min(value = 1, message = "El id de serie documento debe ser mayor a 0")
    private long idSerieDocumento;

    @NotNull(message = "El estado del comprobante es obligatorio")
    private EstadoComprobante estado;

}
