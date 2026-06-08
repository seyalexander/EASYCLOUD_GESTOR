package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestListaCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseListaCierreCaja;

public interface ICierreCajaListado {
    ResponseListaCierreCaja listaCierreCaja(RequestListaCierreCaja request);
}