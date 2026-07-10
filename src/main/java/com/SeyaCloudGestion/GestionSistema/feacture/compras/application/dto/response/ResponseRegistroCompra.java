package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.Data;

@Data
public class ResponseRegistroCompra extends ResponseGeneral {
    private long idCompra;
}
