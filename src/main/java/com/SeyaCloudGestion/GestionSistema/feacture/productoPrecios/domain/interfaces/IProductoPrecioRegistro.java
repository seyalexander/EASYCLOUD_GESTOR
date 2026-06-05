package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.request.RequestRegistroProductoPrecio;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response.ResponseRegistroProductoPrecio;

public interface IProductoPrecioRegistro {
    ResponseRegistroProductoPrecio RegistroProductoPrecio(RequestRegistroProductoPrecio request);
}