package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseListaDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestDetalleVenta;

public interface IDetalleCompraListado {
    ResponseListaDetalleCompra listarDetalleCompra(RequestListaDetalleCompra request);
}
