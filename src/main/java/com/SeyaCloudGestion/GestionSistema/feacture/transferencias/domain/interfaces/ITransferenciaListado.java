package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.request.RequestListaTransferencia;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response.ResponseListaTransferencia;

public interface ITransferenciaListado {
    ResponseListaTransferencia listaTransferencia(RequestListaTransferencia request);
}