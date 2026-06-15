package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces;// Generado a partir de la arquitectura de subFamilia.

import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestListaTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseListaTipoComprobante;

public interface ITipoComprobanteListado {
    ResponseListaTipoComprobante listaTipoComprobante(RequestListaTipoComprobante request);
}
