package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestDetalleCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseDetalleCliente;

public interface IClienteDetalle {
    ResponseDetalleCliente DetalleCliente(RequestDetalleCliente request);
}