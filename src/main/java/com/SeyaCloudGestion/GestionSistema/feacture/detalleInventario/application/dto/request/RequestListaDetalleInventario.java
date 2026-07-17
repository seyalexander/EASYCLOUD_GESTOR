package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class RequestListaDetalleInventario {
    @Min(value = 1, message = "El id inventario cabezera debe ser mayor a 0")
    private long idInventarioCabecera;

    @Min(value = 1, message = "El id del almacen debe ser mayor a 0")
    private long idAlmacecn;
}
