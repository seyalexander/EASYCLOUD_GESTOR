package com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoPagos.infraestructure.persistence.model.TipoPagosModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaTipoPagos extends ResponseGeneral implements Serializable {

    private List<TipoPagosModel> tipoPagos;
}