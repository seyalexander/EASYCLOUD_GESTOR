package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestDetalleAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseDetalleAperturaCaja;

public interface IAperturaCajaDetalle {
    ResponseDetalleAperturaCaja DetalleAperturaCaja(RequestDetalleAperturaCaja request);
}