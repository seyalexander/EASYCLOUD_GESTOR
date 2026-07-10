package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.model.TipoPagoModel;
import lombok.Data;

@Data
public class ResponseDetalleTipoPagos extends ResponseGeneral {

    private TipoPagoModel tipoPagos;
}