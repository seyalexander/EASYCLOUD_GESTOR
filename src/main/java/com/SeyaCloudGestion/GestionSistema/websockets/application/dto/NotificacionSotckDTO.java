package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.stocks.infraestructure.persistence.model.SotckModel;
import lombok.Data;

@Data
public class NotificacionSotckDTO extends SotckModel {
    private String tipo;
    private String mensaje;
}
