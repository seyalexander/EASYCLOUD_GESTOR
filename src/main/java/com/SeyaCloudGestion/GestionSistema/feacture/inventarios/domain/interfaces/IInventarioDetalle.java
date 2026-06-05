package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.request.RequestDetalleInventario;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response.ResponseDetalleInventario;

public interface IInventarioDetalle {
    ResponseDetalleInventario DetalleInventario(RequestDetalleInventario request);
}