package com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestEditarAllCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.request.RequestEditarEstadoCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseEditarAllCierreCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.cierreCaja.application.dto.response.ResponseEditarEstadoCierreCaja;

public interface ICierreCajaEdicion {
    ResponseEditarAllCierreCaja EditarAllCierreCaja(RequestEditarAllCierreCaja request);
    ResponseEditarEstadoCierreCaja EditarEstadoCierreCaja(RequestEditarEstadoCierreCaja request, int estado);
}