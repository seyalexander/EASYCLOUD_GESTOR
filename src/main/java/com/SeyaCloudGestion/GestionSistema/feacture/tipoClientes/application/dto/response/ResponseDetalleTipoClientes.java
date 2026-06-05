package com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoClientes.infraestructure.persistence.model.TipoClientesModel;
import lombok.Data;

@Data
public class ResponseDetalleTipoClientes extends ResponseGeneral {

    private TipoClientesModel tipoClientes;
}