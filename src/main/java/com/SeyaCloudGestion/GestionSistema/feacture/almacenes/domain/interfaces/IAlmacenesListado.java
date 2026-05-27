package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestListaAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseListaAlmacenes;

public interface IAlmacenesListado {
    ResponseListaAlmacenes listaAlmacenes(RequestListaAlmacenes request);
}