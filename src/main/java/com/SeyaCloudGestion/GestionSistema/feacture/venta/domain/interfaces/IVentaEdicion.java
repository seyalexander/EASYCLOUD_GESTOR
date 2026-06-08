package com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestEditarAllVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestEditarEstadoVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseEditarAllVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseEditarEstadoVenta;

public interface IVentaEdicion {
    ResponseEditarAllVenta EditarAllVenta(RequestEditarAllVenta request);
    ResponseEditarEstadoVenta EditarEstadoVenta(RequestEditarEstadoVenta request, int estado);
}