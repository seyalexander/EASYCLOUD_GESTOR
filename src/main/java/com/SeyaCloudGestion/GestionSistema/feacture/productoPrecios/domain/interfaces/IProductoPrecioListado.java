package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestListaProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseListaProductoPrecio;

public interface IProductoPrecioListado {
    ResponseListaProductoPrecio ListaProductoPrecio(RequestListaProductoPrecio request);
}