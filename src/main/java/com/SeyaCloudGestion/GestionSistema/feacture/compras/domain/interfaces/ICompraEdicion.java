package com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestEditarEstadoCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseEditarEstadoCompra;

public interface ICompraEdicion {
    ResponseEditarAllCompra EditarAllCompra(RequestEditarAllCompra request);
    ResponseEditarEstadoCompra EditarEstadoCompra(RequestEditarEstadoCompra request, int estado);
}