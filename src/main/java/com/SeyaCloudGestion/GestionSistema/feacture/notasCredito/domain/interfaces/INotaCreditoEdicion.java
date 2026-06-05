package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestEditarAllNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestEditarEstadoNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseEditarAllNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseEditarEstadoNotaCredito;

public interface INotaCreditoEdicion {
    ResponseEditarAllNotaCredito EditarAllNotaCredito(RequestEditarAllNotaCredito request);
    ResponseEditarEstadoNotaCredito EditarEstadoNotaCredito(RequestEditarEstadoNotaCredito request, int estado);
}