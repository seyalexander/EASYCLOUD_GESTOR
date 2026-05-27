package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestDetalleNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseDetalleNotaCredito;

public interface INotaCreditoDetalle {
    ResponseDetalleNotaCredito DetalleNotaCredito(RequestDetalleNotaCredito request);
}