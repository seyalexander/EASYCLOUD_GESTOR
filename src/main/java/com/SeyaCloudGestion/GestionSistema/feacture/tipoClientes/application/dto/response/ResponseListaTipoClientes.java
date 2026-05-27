package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.model.TipoClientesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaTipoClientes extends ResponseGeneral implements Serializable {

    private List<TipoClientesModel> tipoClientes;
}