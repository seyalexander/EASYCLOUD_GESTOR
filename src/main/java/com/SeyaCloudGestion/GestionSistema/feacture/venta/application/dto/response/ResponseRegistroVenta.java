package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.Data;

@Data
public class ResponseRegistroVenta extends ResponseGeneral {
    private long idVenta;
}