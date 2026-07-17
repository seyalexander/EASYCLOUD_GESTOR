package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import lombok.Data;

@Data
public class ResponseRegistroDetalleInventario extends ResponseGeneral {
    private  double total;
}
