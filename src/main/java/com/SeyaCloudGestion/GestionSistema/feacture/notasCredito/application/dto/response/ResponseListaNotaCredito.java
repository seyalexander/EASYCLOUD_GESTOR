package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.model.NotaCreditoModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaNotaCredito extends ResponseGeneral implements Serializable {

    private List<NotaCreditoModel> notaCreditos;
}