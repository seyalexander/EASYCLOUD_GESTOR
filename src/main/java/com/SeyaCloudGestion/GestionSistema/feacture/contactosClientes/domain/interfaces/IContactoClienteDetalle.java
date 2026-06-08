package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestDetalleContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseDetalleContactoCliente;

public interface IContactoClienteDetalle {
    ResponseDetalleContactoCliente DetalleContactoCliente(RequestDetalleContactoCliente request);
}