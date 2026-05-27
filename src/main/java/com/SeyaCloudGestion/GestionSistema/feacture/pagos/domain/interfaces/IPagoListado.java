package com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestListaPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseListaPago;

public interface IPagoListado {
    ResponseListaPago listaPago(RequestListaPago request);
}