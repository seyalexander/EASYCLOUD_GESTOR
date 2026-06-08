package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.model.ProductoImpuestoModel;
import lombok.Data;

@Data
public class NotificacionProductoImpuestoDTO extends ProductoImpuestoModel {
    private String tipo;
    private String mensaje;
}
