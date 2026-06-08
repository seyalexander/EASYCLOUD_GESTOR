package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.pagos.infraestructure.persistence.model.PagoModel;
import lombok.Data;

@Data
public class NotificacionPagoDTO extends PagoModel {
    private String tipo;
    private String mensaje;
}
