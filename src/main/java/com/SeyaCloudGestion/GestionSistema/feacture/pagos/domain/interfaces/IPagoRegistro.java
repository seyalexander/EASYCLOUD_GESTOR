package com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;

public interface IPagoRegistro {
    ResponseRegistroPago RegistroPago(RequestRegistroPago request);
}