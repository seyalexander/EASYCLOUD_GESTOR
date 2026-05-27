package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestListaAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseListaAperturaCaja;

public interface IAperturaCajaListado {
    ResponseListaAperturaCaja listaAperturaCaja(RequestListaAperturaCaja request);
}