package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.transferencias.infraestructure.persistence.model.TransferenciaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaTransferencia extends ResponseGeneral implements Serializable {

    private List<TransferenciaModel> transferencias;
}