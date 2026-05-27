package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.InventarioModel;
import lombok.Data;

@Data
public class ResponseDetalleInventario extends ResponseGeneral {

    private InventarioModel inventario;
}