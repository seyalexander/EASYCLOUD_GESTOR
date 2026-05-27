package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.model.UnidadMedidaModel;
import lombok.Data;

@Data
public class ResponseDetalleUnidadMedida extends ResponseGeneral {

    private UnidadMedidaModel unidadMedida;
}