package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.request.RequestRegistroNotaCredito;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response.ResponseRegistroNotaCredito;

public interface INotaCreditoRegistro {
    ResponseRegistroNotaCredito RegistroNotaCredito(RequestRegistroNotaCredito request);
}