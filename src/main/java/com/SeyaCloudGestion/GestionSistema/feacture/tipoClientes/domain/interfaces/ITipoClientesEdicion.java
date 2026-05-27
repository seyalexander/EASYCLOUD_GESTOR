package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestEditarEstadoTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarAllTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseEditarEstadoTipoClientes;

public interface ITipoClientesEdicion {
    ResponseEditarAllTipoClientes EditarAllTipoClientes(RequestEditarAllTipoClientes request);
    ResponseEditarEstadoTipoClientes EditarEstadoTipoClientes(RequestEditarEstadoTipoClientes request, int estado);
}