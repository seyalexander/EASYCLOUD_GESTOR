package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestRegistroArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseRegistroArticulo;

public interface IArticulosRegistro {
    ResponseRegistroArticulo registrarArticulo(RequestRegistroArticulo request);
}
