package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.request.RequestDetalleTipoClientes;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response.ResponseDetalleTipoClientes;

public interface ITipoClientesDetalle {
    ResponseDetalleTipoClientes DetalleTipoClientes(RequestDetalleTipoClientes request);
}