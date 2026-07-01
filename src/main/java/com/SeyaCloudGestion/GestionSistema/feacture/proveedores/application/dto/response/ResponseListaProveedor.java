package com.SeyaCloudGestion.GestionSistema.feacture.proveedores.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.proveedores.infraestructure.persistence.model.ProveedorModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaProveedor extends ResponseGeneral implements Serializable {

    private List<ProveedorModel> proveedores;
}