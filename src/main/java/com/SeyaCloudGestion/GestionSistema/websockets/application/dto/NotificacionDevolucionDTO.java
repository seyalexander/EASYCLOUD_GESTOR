package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.devoluciones.infraestructure.persistence.model.DevolucionModel;
import lombok.Data;

@Data
public class NotificacionDevolucionDTO extends DevolucionModel {
    private String tipo;
    private String mensaje;
}
