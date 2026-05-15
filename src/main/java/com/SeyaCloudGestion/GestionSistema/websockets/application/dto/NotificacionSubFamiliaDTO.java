package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.model.subFamiliaModel;
import lombok.Data;

@Data
public class NotificacionSubFamiliaDTO extends subFamiliaModel {
    private String tipo;
    private String mensaje;
}
