package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.model.ProductoPrecioModel;
import lombok.Data;

@Data
public class NotificacionProductoPrecioDTO extends ProductoPrecioModel {
    private String tipo;
    private String mensaje;
}
