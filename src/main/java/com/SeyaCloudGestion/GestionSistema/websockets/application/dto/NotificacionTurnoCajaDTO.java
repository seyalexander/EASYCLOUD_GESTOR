package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.turnoCaja.infraestructure.persistence.model.TurnoCajaModel;
import lombok.Data;

@Data
public class NotificacionTurnoCajaDTO extends TurnoCajaModel {
    private String tipo;
    private String mensaje;
}
