package com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.comprobantes.infraestructure.persistence.model.ComprobanteModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaComprobante extends ResponseGeneral implements Serializable {

    private List<ComprobanteModel> comprobantes;
}