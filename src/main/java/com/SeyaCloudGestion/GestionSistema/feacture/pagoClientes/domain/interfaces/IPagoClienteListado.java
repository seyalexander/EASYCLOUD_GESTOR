package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestListaPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseListaPagoCliente;

public interface IPagoClienteListado {
    ResponseListaPagoCliente listaPagoCliente(RequestListaPagoCliente request);
}