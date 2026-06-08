package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.ventaResumenDiario.infraestructure.persistence.model.VentaResumenDiarioModel;
import lombok.Data;

@Data
public class NotificacionVentaResumenDiarioDTO extends VentaResumenDiarioModel {
    private String tipo;
    private String mensaje;
}
