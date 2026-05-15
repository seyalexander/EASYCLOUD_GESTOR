package com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.unidadMedida.infraestructure.persistence.model.UnidadMedidaModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaUnidadMedida extends ResponseGeneral {
    private List<UnidadMedidaModel> unidadesMedida;
}
