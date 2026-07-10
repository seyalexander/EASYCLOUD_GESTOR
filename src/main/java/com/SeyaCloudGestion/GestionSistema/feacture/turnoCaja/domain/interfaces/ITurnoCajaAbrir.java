package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestAbrirTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseAbrirTurnoCaja;

public interface ITurnoCajaAbrir {
    ResponseAbrirTurnoCaja AbrirTurnoCaja(RequestAbrirTurnoCaja request);
}