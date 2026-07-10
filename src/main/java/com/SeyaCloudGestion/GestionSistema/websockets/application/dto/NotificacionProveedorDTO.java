package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedorModel;
import lombok.Data;

@Data
public class NotificacionProveedorDTO extends ProveedorModel {
    private String tipo;
    private String mensaje;
}
