package com.SeyaCloudGestion.GestionSistema.feacture.moneda.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.request.RequestDetalleMoneda;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response.ResponseDetalleMoneda;

public interface IMonedaDetalle {
    ResponseDetalleMoneda DetalleMoneda(RequestDetalleMoneda request);
}
