package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.model.MarcaModel;
import lombok.Data;

@Data
public class NotificacionMarcaDTO extends MarcaModel {
    private String tipo;
    private String mensaje;
}
