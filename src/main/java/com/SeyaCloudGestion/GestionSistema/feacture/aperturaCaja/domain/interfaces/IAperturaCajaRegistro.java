package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestRegistroAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseRegistroAperturaCaja;

public interface IAperturaCajaRegistro {
    ResponseRegistroAperturaCaja RegistroAperturaCaja(RequestRegistroAperturaCaja request);
}