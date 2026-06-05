package com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.request.RequestDetalleDevolucion;
import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.application.dto.response.ResponseDetalleDevolucion;

public interface IDevolucionDetalle {
    ResponseDetalleDevolucion DetalleDevolucion(RequestDetalleDevolucion request);
}