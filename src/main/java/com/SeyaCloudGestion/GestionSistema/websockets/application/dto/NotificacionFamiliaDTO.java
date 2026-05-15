package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
import lombok.Data;

@Data
public class NotificacionFamiliaDTO extends FamiliaModel {
    private String tipo;
    private String mensaje;
}
