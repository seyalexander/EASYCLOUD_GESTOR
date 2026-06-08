package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenesModel;
import lombok.Data;

@Data
public class NotificacionAlmacenDTO extends AlmacenesModel {
    private String tipo;
    private String mensaje;
}
