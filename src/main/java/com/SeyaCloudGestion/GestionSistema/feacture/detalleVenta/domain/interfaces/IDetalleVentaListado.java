package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseListaDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;

public interface IDetalleVentaListado {
    ResponseListaDetalleVenta listarDetalleVenta(RequestDetalleVenta request);
}
