package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarEstadoAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarAllAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarEstadoAlmacenes;

public interface IAlmacenesEdicion {
    ResponseEditarAllAlmacenes EditarAllAlmacenes(RequestEditarAllAlmacenes request);
    ResponseEditarEstadoAlmacenes EditarEstadoAlmacenes(RequestEditarEstadoAlmacenes request, int estado);
}