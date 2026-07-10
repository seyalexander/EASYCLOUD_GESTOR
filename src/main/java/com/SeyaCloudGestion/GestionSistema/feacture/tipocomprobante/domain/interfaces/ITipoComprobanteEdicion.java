package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces;// Generado a partir de la arquitectura de subFamilia.

import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestEditarAllTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestEditarEstadoTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseEditarAllTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseEditarEstadoTipoComprobante;

public interface ITipoComprobanteEdicion {
    ResponseEditarAllTipoComprobante EditarAllTipoComprobante(RequestEditarAllTipoComprobante request);
    ResponseEditarEstadoTipoComprobante EditarEstadoTipoComprobante(RequestEditarEstadoTipoComprobante request, int estado);
}
