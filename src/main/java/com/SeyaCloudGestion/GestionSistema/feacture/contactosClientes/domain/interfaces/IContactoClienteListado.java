package com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.request.RequestListaContactoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.contactosClientes.application.dto.response.ResponseListaContactoCliente;

public interface IContactoClienteListado {
    ResponseListaContactoCliente listaContactoCliente(RequestListaContactoCliente request);
}