package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestDetalleComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseDetalleComprobante;

public interface IComprobanteDetalle {
    ResponseDetalleComprobante DetalleComprobante(RequestDetalleComprobante request);
}