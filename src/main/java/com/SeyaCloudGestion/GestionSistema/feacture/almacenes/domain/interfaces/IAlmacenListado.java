package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestListaAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseListaAlmacen;

public interface IAlmacenListado {
    ResponseListaAlmacen ListaAlmacen(RequestListaAlmacen request);
}