package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.VentaModel;
import lombok.Data;

@Data
public class NotificacionVentaDTO extends VentaModel {
    private String tipo;
    private String mensaje;
}
