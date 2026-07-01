package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.request.RequestRegistroDetalleCompra;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response.ResponseRegistroDetalleCompra;

public interface IDetalleCompraRegistro {
    ResponseRegistroDetalleCompra registrarDetalleCompra(RequestRegistroDetalleCompra request, double total);
}
