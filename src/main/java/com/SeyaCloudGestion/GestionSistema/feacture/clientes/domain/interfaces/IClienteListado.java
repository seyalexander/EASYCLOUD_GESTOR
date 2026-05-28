package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestListaCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseListaCliente;

public interface IClienteListado {
    ResponseListaCliente ListaCliente(RequestListaCliente request);
}