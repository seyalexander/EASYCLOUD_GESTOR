package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.aperturaCaja.infraestructure.persistence.model.AperturaCajaModel;
import lombok.Data;

@Data
public class NotificacionAperturaCajaDTO extends AperturaCajaModel {
    private String tipo;
    private String mensaje;
}
