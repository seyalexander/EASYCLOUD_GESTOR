package com.SeyaCloudGestion.GestionSistema.feacture.transferencias.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.Data;

@Data
public class ResponseRegistroTransferencia extends ResponseGeneral {
    private long idTransferenciaCabecera;
}
