package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.rol.infraestructure.persistence.model.RolModel;

import lombok.Data;

@Data
public class NotificacionRolDTO extends RolModel {
    private String tipo;
    private String mensaje;
}
