package com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseDetalleCompra;

public interface ICompraDetalle {
    ResponseDetalleCompra DetalleCompra(RequestDetalleCompra request);
}