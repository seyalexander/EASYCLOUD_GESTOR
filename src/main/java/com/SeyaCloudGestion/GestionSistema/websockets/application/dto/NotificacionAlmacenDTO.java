package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenModel;
import lombok.Data;

@Data
public class NotificacionAlmacenDTO extends AlmacenModel {
    private String tipo;
    private String mensaje;
}
