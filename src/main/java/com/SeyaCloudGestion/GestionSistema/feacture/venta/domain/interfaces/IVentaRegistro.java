package com.SeyaCloudGestion.GestionSistema.feacture.venta.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.request.RequestRegistroVenta;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response.ResponseRegistroVenta;
import jakarta.validation.constraints.PositiveOrZero;

public interface IVentaRegistro {
    ResponseRegistroVenta RegistroVenta(long idCaja,RequestRegistroVenta request, double subTotal, double impuesto, double total);
}