package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.request.RequestListaDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response.ResponseListaDetalleInventario;

public interface IDetalleInventarioListado {
    ResponseListaDetalleInventario listarDetalleInventario(RequestListaDetalleInventario request);
}
