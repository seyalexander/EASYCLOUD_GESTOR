package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestListaNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseListaNotaCredito;

public interface INotaCreditoListado {
    ResponseListaNotaCredito listaNotaCredito(RequestListaNotaCredito request);
}