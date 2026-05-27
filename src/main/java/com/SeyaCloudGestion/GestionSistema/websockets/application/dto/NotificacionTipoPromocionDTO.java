package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.tipoPromociones.infraestructure.persistence.model.TipoPromocionesModel;
import lombok.Data;

@Data
public class NotificacionTipoPromocionDTO extends TipoPromocionesModel {
    private String tipo;
    private String mensaje;
}
