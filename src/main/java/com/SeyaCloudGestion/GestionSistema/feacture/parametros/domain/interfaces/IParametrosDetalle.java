package com.SeyaCloudGestion.GestionSistema.feacture.parametros.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.request.RequestDetalleParametros;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response.ResponseDetalleParametros;

public interface IParametrosDetalle {
    ResponseDetalleParametros DetalleParametros(RequestDetalleParametros request);
}