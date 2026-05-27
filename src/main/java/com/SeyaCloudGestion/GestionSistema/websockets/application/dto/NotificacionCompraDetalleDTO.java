package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.model.CompraDetalleModel;
import lombok.Data;

@Data
public class NotificacionCompraDetalleDTO extends CompraDetalleModel {
    private String tipo;
    private String mensaje;
}
