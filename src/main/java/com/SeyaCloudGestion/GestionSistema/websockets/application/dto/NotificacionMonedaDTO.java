package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.model.MonedaModel;
import lombok.Data;

@Data
public class NotificacionMonedaDTO extends MonedaModel {
    private String tipo;
    private String mensaje;
}
