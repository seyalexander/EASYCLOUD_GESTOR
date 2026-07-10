package com.SeyaCloudGestion.GestionSistema.feacture.pagos.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.request.RequestRegistroPago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroDetallePago;
import com.SeyaCloudGestion.GestionSistema.feacture.pagos.application.dto.response.ResponseRegistroPago;

public interface IPagoRegistro {
    ResponseRegistroDetallePago RegistroPago(long idVenta, RequestRegistroDetallePago request);
}