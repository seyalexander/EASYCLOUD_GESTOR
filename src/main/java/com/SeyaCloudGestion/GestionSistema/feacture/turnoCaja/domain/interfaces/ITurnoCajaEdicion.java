package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestEditarAllTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestEditarEstadoTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseEditarAllTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseEditarEstadoTurnoCaja;

public interface ITurnoCajaEdicion {
    ResponseEditarAllTurnoCaja EditarAllTurnoCaja(RequestEditarAllTurnoCaja request);
    ResponseEditarEstadoTurnoCaja EditarEstadoTurnoCaja(RequestEditarEstadoTurnoCaja request, int estado);
}