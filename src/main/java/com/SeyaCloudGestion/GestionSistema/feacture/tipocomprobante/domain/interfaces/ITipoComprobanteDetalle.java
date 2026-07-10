package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces;// Generado a partir de la arquitectura de subFamilia.

import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestDetalleTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseDetalleTipoComprobante;

public interface ITipoComprobanteDetalle {
    ResponseDetalleTipoComprobante DetalleTipoComprobante(RequestDetalleTipoComprobante request);
}
