package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestEditarAllComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestEditarEstadoComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseEditarAllComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseEditarEstadoComprobante;

public interface IComprobanteEdicion {
    ResponseEditarAllComprobante EditarAllComprobante(RequestEditarAllComprobante request);
    ResponseEditarEstadoComprobante EditarEstadoComprobante(RequestEditarEstadoComprobante request, int estado);
}