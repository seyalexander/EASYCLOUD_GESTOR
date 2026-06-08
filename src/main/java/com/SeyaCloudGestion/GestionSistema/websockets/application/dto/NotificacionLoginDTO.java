package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.login.infraestructure.persistence.model.LoginModel;
import lombok.Data;

@Data
public class NotificacionLoginDTO extends LoginModel {
    private String tipo;
    private String mensaje;
}
