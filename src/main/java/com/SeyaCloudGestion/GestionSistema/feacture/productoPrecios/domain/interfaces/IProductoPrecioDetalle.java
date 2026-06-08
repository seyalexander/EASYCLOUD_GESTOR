package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestDetalleProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseDetalleProductoPrecio;

public interface IProductoPrecioDetalle {
    ResponseDetalleProductoPrecio DetalleProductoPrecio(RequestDetalleProductoPrecio request);
}