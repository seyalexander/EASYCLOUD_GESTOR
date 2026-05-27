package com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestListaVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseListaVenta;

public interface IVentaListado {
    ResponseListaVenta listaVenta(RequestListaVenta request);
}