package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RequestRegistrarAjusteInventarioInicial {
    @Min(value = 1, message = "El id debe ser mayor a 0")
    private long idAlmacen;

    @NotEmpty(message = "Debe registrar al menos un producto para realizar la compra")
    private List<RequestRegistroDetalleCompra> detalles;
}
