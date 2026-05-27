package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedoresModel;
import lombok.Data;

@Data
public class ResponseDetalleProveedores extends ResponseGeneral {

    private ProveedoresModel proveedores;
}