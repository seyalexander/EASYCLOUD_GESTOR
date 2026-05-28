package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestListaTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseListaTipoClientes;

public interface ITipoClientesListado {
    ResponseListaTipoClientes ListaTipoClientes(RequestListaTipoClientes request);
}