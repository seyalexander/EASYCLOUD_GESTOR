package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestDetalleArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseDetalleArticulo;

public interface IArticulosDetalle {
    ResponseDetalleArticulo DetalleArticulos(RequestDetalleArticulo request);
}