package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.TurnoCajaModel;
import lombok.Data;

@Data
public class ResponseDetalleTurnoCaja extends ResponseGeneral {

    private TurnoCajaModel turnoCaja;
}