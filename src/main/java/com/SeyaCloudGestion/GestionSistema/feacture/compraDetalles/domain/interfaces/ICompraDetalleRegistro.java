package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.request.RequestRegistroCompraDetalle;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response.ResponseRegistroCompraDetalle;

public interface ICompraDetalleRegistro {
    ResponseRegistroCompraDetalle RegistroCompraDetalle(RequestRegistroCompraDetalle request);
}