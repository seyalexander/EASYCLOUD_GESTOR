package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.Data;

@Data
public class ResponseRegistroDetalleVenta extends ResponseGeneral {
    private  double total;
}
