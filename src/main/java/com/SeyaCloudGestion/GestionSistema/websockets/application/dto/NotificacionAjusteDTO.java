package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.model.AjustesModel;
import lombok.Data;

@Data
public class NotificacionAjusteDTO extends AjustesModel {
    private String tipo;
    private String mensaje;
}
