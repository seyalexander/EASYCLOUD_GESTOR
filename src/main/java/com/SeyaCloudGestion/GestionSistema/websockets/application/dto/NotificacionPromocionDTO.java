package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.promociones.infraestructure.persistence.model.PromocionesModel;
import lombok.Data;

@Data
public class NotificacionPromocionDTO extends PromocionesModel {
    private String tipo;
    private String mensaje;
}
