package com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.request.RequestDetalleListaPrecios;
import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.application.dto.response.ResponseDetalleListaPrecios;

public interface IListaPreciosDetalle {
    ResponseDetalleListaPrecios DetalleListaPrecios(RequestDetalleListaPrecios request);
}