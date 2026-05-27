package com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.request.RequestListaCuentasPorCobrar;
import com.SeyaCloudGestion.GestionSistema.feacture.cuentasPorCobrar.application.dto.response.ResponseListaCuentasPorCobrar;

public interface ICuentasPorCobrarListado {
    ResponseListaCuentasPorCobrar listaCuentasPorCobrar(RequestListaCuentasPorCobrar request);
}