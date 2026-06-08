package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.guias.infraestructure.persistence.model.GuiasIngresoModel;
import lombok.Data;

@Data
public class NotificacionGuiaIngresoDTO extends GuiasIngresoModel {
    private String tipo;
    private String mensaje;
}
