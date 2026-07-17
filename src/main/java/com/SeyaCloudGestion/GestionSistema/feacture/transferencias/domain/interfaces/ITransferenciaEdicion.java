package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestAceptrarTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseAceptarTransferencia;

public interface ITransferenciaEdicion {
    //ResponseEditarAllTransferencia EditarAllTransferencia(RequestEditarAllTransferencia request);
    ResponseAceptarTransferencia EditarEstadoTransferencia(RequestAceptrarTransferencia request);
}