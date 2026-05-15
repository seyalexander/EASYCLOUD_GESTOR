package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.model.MonedaModel;
import lombok.Data;

@Data
public class ResponseDetalleMoneda extends ResponseGeneral {
    MonedaModel moneda;
}
