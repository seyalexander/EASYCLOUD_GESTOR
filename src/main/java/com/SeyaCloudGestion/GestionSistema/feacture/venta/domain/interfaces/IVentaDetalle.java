package com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseDetalleVenta;

public interface IVentaDetalle {
    ResponseDetalleVenta DetalleVenta(RequestDetalleVenta request);
}