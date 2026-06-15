package com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.application.dto.response;// Generado a partir de la arquitectura de subFamilia.

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipocomprobante.infraestructure.persistence.model.TipoComprobanteModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaTipoComprobante extends ResponseGeneral implements Serializable {
    private List<TipoComprobanteModel> tipoComprobante;
}
