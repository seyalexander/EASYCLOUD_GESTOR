package com.SeyaCloudGestion.GestionSistema.feacture.clientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.request.RequestRegistroCliente;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response.ResponseRegistroCliente;

public interface IClienteRegistro {
    ResponseRegistroCliente RegistroCliente(RequestRegistroCliente request);
}