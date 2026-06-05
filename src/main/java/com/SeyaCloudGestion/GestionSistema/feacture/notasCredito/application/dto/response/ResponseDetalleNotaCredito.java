package com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.notasCredito.infraestructure.persistence.model.NotaCreditoModel;
import lombok.Data;

@Data
public class ResponseDetalleNotaCredito extends ResponseGeneral {

    private NotaCreditoModel notaCredito;
}