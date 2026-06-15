package com.SeyaCloudGestion.GestionSistema.feacture.caja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.request.RequestListaCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.caja.application.dto.response.ResponseListaCaja;

public interface ICajaListado {
    ResponseListaCaja listaCaja();
}