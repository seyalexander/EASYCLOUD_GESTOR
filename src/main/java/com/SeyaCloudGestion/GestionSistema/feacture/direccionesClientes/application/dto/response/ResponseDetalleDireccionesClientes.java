package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.model.DireccionesClientesModel;
import lombok.Data;

@Data
public class ResponseDetalleDireccionesClientes extends ResponseGeneral {

    private DireccionesClientesModel direccionesClientes;
}