package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model.ComprobanteModel;
import lombok.Data;

@Data
public class ResponseDetalleComprobante extends ResponseGeneral {

    private ComprobanteModel comprobante;
}