package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestRegistroTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseRegistroTipoClientes;

public interface ITipoClientesRegistro {
    ResponseRegistroTipoClientes RegistroTipoClientes(RequestRegistroTipoClientes request);
}