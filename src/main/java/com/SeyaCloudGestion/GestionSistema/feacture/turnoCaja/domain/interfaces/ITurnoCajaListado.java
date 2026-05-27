package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestListaTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseListaTurnoCaja;

public interface ITurnoCajaListado {
    ResponseListaTurnoCaja listaTurnoCaja(RequestListaTurnoCaja request);
}