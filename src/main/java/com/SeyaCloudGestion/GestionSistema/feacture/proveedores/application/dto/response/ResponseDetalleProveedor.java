package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedorModel;
import lombok.Data;

@Data
public class ResponseDetalleProveedor extends ResponseGeneral {

    private ProveedorModel proveedor;
}