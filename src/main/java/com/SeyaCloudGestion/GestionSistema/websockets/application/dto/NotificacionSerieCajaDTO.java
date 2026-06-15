package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.model.SerieCajaModel;
import lombok.Data;

@Data
public class NotificacionSerieCajaDTO extends SerieCajaModel {
    private String tipo;
    private String mensaje;
}
