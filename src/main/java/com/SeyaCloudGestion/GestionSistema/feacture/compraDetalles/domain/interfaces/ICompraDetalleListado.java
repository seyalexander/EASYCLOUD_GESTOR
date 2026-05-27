package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestListaCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseListaCompraDetalle;

public interface ICompraDetalleListado {
    ResponseListaCompraDetalle listaCompraDetalle(RequestListaCompraDetalle request);
}