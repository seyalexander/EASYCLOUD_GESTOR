package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.Data;

@Data
public class ResponseRegistroDetalleCompra extends ResponseGeneral {
    private  double total;
}
