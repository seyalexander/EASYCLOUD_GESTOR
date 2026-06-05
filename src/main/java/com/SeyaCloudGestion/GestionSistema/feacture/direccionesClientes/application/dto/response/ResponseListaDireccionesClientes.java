package com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.direccionesClientes.infraestructure.persistence.model.DireccionesClientesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaDireccionesClientes extends ResponseGeneral implements Serializable {

    private List<DireccionesClientesModel> direccionesClientes;
}