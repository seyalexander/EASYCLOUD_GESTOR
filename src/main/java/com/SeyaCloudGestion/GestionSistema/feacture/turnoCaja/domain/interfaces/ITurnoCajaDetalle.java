package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.domain.interfaces;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.request.RequestDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response.ResponseDetalleTurnoCaja;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.EstadoCaja;

public interface ITurnoCajaDetalle {
    ResponseDetalleTurnoCaja DetalleTurnoCaja(RequestDetalleTurnoCaja request, EstadoCaja estado);
}