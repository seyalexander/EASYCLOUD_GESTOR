package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestDetalleCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseDetalleCompraDetalle;

public interface ICompraDetalleDetalle {
    ResponseDetalleCompraDetalle DetalleCompraDetalle(RequestDetalleCompraDetalle request);
}