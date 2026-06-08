package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.KardexModel;
import lombok.Data;

@Data
public class NotificacionKardexDTO extends KardexModel {
    private String tipo;
    private String mensaje;
}
