package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestEditarDetalleInventario;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import jakarta.validation.constraints.Min;

import java.util.List;

@Data
public class RequestConteoFisicoInventario {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idInventarioCabecera;

    @NotEmpty(message = "Debe registrar al menos un articulo para realizar el inventario")
    private List<RequestEditarDetalleInventario> detalles;
}
