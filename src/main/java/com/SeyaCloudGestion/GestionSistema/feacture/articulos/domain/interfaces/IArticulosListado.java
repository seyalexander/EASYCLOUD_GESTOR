package com.SeyaCloudGestion.GestionSistema.feacture.articulos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.request.RequestListaArticulo;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response.ResponseListaArticulo;

public interface IArticulosListado {
    ResponseListaArticulo ListaArticulos(RequestListaArticulo request);
}