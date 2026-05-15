package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.empleados.infraestructure.persistence.model.EmpleadoModel;
import lombok.Data;

@Data
public class NotificacionEmpleadoDTO extends EmpleadoModel {
    private String tipo;
    private String mensaje;
}
