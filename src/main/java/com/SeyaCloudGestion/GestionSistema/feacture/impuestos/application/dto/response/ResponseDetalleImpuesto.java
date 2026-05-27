package com.SeyaCloudGestion.GestionSistema.feacture.impuestos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.impuestos.infraestructure.persistence.model.ImpuestoModel;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseDetalleImpuesto extends ResponseGeneral implements Serializable {

    private ImpuestoModel impuesto;
}