package com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.request.RequestListaPagoProveedores;
import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.application.dto.response.ResponseListaPagoProveedores;

public interface IPagoProveedoresListado {
    ResponseListaPagoProveedores listaPagoProveedores(RequestListaPagoProveedores request);
}