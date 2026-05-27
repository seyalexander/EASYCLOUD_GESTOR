package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestDetallePagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseDetallePagoCliente;

public interface IPagoClienteDetalle {
    ResponseDetallePagoCliente DetallePagoCliente(RequestDetallePagoCliente request);
}