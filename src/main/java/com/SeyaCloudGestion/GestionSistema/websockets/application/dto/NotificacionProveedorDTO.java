package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedoresModel;
import lombok.Data;

@Data
public class NotificacionProveedorDTO extends ProveedoresModel {
    private String tipo;
    private String mensaje;
}
