package com.SeyaCloudGestion.GestionSistema.feacture.clientes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.clientes.infraestructure.persistence.model.ClienteModel;
import lombok.Data;

@Data
public class ResponseDetalleCliente extends ResponseGeneral {
    private ClienteModel cliente;
}