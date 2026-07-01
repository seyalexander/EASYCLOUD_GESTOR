package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.request.RequestRegistroDetalleVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response.ResponseRegistroDetalleVenta;

public interface IDetalleVentaRegistro {
    ResponseRegistroDetalleVenta registrarDetalleVenta(RequestRegistroDetalleVenta request, double total, double costoUnitario);
}
