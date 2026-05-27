package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.request.RequestRegistroTipoMovimiento;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response.ResponseRegistroTipoMovimiento;

public interface ITipoMovimientoRegistro {
    ResponseRegistroTipoMovimiento RegistroTipoMovimiento(RequestRegistroTipoMovimiento request);
}