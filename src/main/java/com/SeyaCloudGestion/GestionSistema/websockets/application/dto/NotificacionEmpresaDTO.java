package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.empresa.infraestructure.persistence.model.EmpresaModel;
import lombok.Data;

@Data
public class NotificacionEmpresaDTO extends EmpresaModel {
    private String tipo;
    private String mensaje;
}
