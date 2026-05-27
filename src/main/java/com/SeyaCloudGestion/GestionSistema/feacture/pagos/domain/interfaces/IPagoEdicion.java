package com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestEditarAllPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestEditarEstadoPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseEditarAllPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseEditarEstadoPago;

public interface IPagoEdicion {
    ResponseEditarAllPago EditarAllPago(RequestEditarAllPago request);
    ResponseEditarEstadoPago EditarEstadoPago(RequestEditarEstadoPago request, int estado);
}