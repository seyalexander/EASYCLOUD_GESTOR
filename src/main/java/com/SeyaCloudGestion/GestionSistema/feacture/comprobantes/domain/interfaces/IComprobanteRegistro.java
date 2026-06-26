package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.request.RequestRegistroComprobante;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response.ResponseRegistroComprobante;

public interface IComprobanteRegistro {
    ResponseRegistroComprobante RegistroComprobante(RequestRegistroComprobante request,String urlXml,String urlPdf,String numero);
}