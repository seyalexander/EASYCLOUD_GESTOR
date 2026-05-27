package com.SeyaCloudGestion.GestionSistema.websockets.application.dto;

import com.SeyaCloudGestion.GestionSistema.feacture.pagoProveedores.infraestructure.persistence.model.PagoProveedoresModel;
import lombok.Data;

@Data
public class NotificacionPagoProveedorDTO extends PagoProveedoresModel {
    private String tipo;
    private String mensaje;
}
