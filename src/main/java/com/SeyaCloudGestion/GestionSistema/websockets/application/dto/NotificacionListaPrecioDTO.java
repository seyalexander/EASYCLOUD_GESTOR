package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.listaPrecios.infraestructure.persistence.model.ListaPreciosModel;
import lombok.Data;

@Data
public class NotificacionListaPrecioDTO extends ListaPreciosModel {
    private String tipo;
    private String mensaje;
}
