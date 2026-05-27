package com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestListaCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseListaCompra;

public interface ICompraListado {
    ResponseListaCompra listaCompra(RequestListaCompra request);
}