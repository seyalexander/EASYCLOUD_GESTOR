package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response;// Generado a partir de la arquitectura de subFamilia.

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.model.TipoComprobanteModel;
import lombok.Data;

@Data
public class ResponseDetalleTipoComprobante extends ResponseGeneral {
    TipoComprobanteModel tipoCompobante;
}
