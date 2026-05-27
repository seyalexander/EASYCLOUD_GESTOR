package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.model.CompraModel;
import lombok.Data;

@Data
public class NotificacionCompraDTO extends CompraModel {
    private String tipo;
    private String mensaje;
}
