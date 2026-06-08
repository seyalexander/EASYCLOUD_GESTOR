package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model.TransferenciaModel;
import lombok.Data;

@Data
public class ResponseDetalleTransferencia extends ResponseGeneral {

    private TransferenciaModel transferencia;
}