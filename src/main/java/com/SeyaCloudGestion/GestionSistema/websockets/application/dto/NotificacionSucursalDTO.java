package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.sucursales.infraestructure.persistence.model.SucursalesModel;
import lombok.Data;

@Data
public class NotificacionSucursalDTO extends SucursalesModel {
    private String tipo;
    private String mensaje;
}
