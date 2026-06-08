package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.model.ClienteModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCliente extends ResponseGeneral implements Serializable {

    private List<ClienteModel> clientes;
}