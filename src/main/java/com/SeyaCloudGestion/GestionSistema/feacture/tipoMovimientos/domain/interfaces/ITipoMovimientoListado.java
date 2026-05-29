package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestListaTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseListaTipoMovimiento;

public interface ITipoMovimientoListado {
    ResponseListaTipoMovimiento ListaTipoMovimiento(RequestListaTipoMovimiento request);
}