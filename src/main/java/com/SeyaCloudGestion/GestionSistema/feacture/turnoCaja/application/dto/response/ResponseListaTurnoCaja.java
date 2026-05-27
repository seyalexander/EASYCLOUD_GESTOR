package com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.TurnoCajaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaTurnoCaja extends ResponseGeneral implements Serializable {

    private List<TurnoCajaModel> turnoCajas;
}