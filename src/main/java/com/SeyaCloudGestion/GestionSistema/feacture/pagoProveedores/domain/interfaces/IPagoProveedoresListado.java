package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestListaPagoProveedor;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseListaPagoProveedor;

public interface IPagoProveedoresListado {
    ResponseListaPagoProveedor listaPagoProveedor(RequestListaPagoProveedor request);
}