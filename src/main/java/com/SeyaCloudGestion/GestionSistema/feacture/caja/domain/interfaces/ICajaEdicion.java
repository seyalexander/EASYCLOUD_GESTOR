package com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestEditarAllCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseEditarAllCaja;

public interface ICajaEdicion {
    ResponseEditarAllCaja EditarAllCaja(RequestEditarAllCaja request);
}