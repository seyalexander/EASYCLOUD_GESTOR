package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.model.ImpuestoModel;
import lombok.Data;

@Data
public class ResponseDetalleImpuesto extends ResponseGeneral {

    private ImpuestoModel impuesto;
}