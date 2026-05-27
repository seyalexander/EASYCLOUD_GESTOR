package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestListaComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseListaComprobante;

public interface IComprobanteListado {
    ResponseListaComprobante listaComprobante(RequestListaComprobante request);
}