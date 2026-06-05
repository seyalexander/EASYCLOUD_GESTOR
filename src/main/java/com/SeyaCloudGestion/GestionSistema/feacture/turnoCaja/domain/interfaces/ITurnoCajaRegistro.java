package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestRegistroTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseRegistroTurnoCaja;

public interface ITurnoCajaRegistro {
    ResponseRegistroTurnoCaja RegistroTurnoCaja(RequestRegistroTurnoCaja request);
}