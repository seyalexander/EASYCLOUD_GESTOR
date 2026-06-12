package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestCerrarTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseCerrarTurnoCaja;

public interface ITurnoCajaCerrar {
    ResponseCerrarTurnoCaja CerrarTurnoCaja(RequestCerrarTurnoCaja request,double montoSistema,double diferencia);
}