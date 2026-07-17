package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request;

import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.request.RequestRegistroDetalleTransferencia;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

import java.util.List;

@Data
public class RequestRegistroTransferencia {

    @Min(value = 1, message = "El id del alamcen origen debe ser mayor a 0")
    private long idAlmacenOrigen;

    @Min(value = 1, message = "El id del almacen destino debe ser mayor a 0")
    private long idAlmacenDestino;

    @NotEmpty(message = "Debe registrar al menos un articulo para realizar la transferencia")
    private List<RequestRegistroDetalleTransferencia> detalles;
}
