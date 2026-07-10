package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarAllAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestEditarEstadoAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarAllAlmacen;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseEditarEstadoAlmacen;

public interface IAlmacenEdicion {
    ResponseEditarAllAlmacen EditarAllAlmacen(RequestEditarAllAlmacen request);
    ResponseEditarEstadoAlmacen EditarEstadoAlmacen(RequestEditarEstadoAlmacen request, int estado);
}