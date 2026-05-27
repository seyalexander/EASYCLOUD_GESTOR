package com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseRegistroVenta;

public interface IVentaRegistro {
    ResponseRegistroVenta RegistroVenta(RequestRegistroVenta request);
}