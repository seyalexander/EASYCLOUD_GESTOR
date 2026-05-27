package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.request.RequestListaCuentasPorPagar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorPagar.application.dto.response.ResponseListaCuentasPorPagar;

public interface ICuentasPorPagarListado {
    ResponseListaCuentasPorPagar listaCuentasPorPagar(RequestListaCuentasPorPagar request);
}