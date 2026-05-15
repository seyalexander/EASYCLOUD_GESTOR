package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.model.ArticulosModel;
import lombok.Data;

@Data
public class NotificacionArticuloDTO extends ArticulosModel {
    private String tipo;
    private String mensaje;
}
