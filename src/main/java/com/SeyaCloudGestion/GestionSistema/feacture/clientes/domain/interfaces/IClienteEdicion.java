package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestEditarEstadoCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarAllCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseEditarEstadoCliente;

public interface IClienteEdicion {
    ResponseEditarAllCliente EditarAllCliente(RequestEditarAllCliente request);
    ResponseEditarEstadoCliente EditarEstadoCliente(RequestEditarEstadoCliente request, int estado);
}