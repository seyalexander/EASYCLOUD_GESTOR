package com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestEditarAllAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.request.RequestEditarEstadoAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseEditarAllAperturaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.application.dto.response.ResponseEditarEstadoAperturaCaja;

public interface IAperturaCajaEdicion {
    ResponseEditarAllAperturaCaja EditarAllAperturaCaja(RequestEditarAllAperturaCaja request);
    ResponseEditarEstadoAperturaCaja EditarEstadoAperturaCaja(RequestEditarEstadoAperturaCaja request, int estado);
}