package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.InventarioModel;
import lombok.Data;

@Data
public class NotificacionInventarioDTO extends InventarioModel {
    private String tipo;
    private String mensaje;
}
