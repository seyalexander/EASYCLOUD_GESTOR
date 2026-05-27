package com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.request.RequestDetalleMovimientoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.movimientosCaja.application.dto.response.ResponseDetalleMovimientoCaja;

public interface IMovimientoCajaDetalle {
    ResponseDetalleMovimientoCaja DetalleMovimientoCaja(RequestDetalleMovimientoCaja request);
}