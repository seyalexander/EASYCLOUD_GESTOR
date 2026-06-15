package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.domain.interfaces;// Generado a partir de la arquitectura de subFamilia.

import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.request.RequestRegistroTipoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response.ResponseRegistroTipoComprobante;

public interface ITipoComprobanteRegistro {
    ResponseRegistroTipoComprobante RegistroTipoComprobante(RequestRegistroTipoComprobante request);
}
