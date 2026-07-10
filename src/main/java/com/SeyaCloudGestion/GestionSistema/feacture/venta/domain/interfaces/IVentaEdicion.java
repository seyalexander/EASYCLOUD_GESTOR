package com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestEditarEstadoVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseEditarEstadoVenta;

public interface IVentaEdicion {
    ResponseEditarEstadoVenta EditarEstadoVenta(RequestEditarEstadoVenta request, int estado);
}