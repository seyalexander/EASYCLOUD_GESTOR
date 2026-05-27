package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model.TransferenciaModel;
import lombok.Data;

@Data
public class NotificacionTransferenciaDTO extends TransferenciaModel {
    private String tipo;
    private String mensaje;
}
