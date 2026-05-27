package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestDetalleTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseDetalleTransferencia;

public interface ITransferenciaDetalle {
    ResponseDetalleTransferencia DetalleTransferencia(RequestDetalleTransferencia request);
}