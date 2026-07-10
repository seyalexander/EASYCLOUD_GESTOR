package com.SeyaCloudGestion.GestionSistema.feacture.compras.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.request.RequestAnularCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseEditarAllCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response.ResponseAnularCompra;

public interface ICompraEdicion {
    ResponseEditarAllCompra EditarAllCompra(RequestEditarAllCompra request);
    ResponseAnularCompra AnularCompra(RequestAnularCompra request, int estado);
}