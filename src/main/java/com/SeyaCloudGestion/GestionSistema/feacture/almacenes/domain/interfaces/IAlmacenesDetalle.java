package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.request.RequestDetalleAlmacenes;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response.ResponseDetalleAlmacenes;

public interface IAlmacenesDetalle {
    ResponseDetalleAlmacenes DetalleAlmacenes(RequestDetalleAlmacenes request);
}