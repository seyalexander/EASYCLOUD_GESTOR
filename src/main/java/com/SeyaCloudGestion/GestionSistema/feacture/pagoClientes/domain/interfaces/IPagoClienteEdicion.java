package com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestEditarAllPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.request.RequestEditarEstadoPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseEditarAllPagoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoClientes.application.dto.response.ResponseEditarEstadoPagoCliente;

public interface IPagoClienteEdicion {
    ResponseEditarAllPagoCliente EditarAllPagoCliente(RequestEditarAllPagoCliente request);
    ResponseEditarEstadoPagoCliente EditarEstadoPagoCliente(RequestEditarEstadoPagoCliente request, int estado);
}