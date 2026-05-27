package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.model.ParametrosModel;
import lombok.Data;

@Data
public class ResponseDetalleParametros extends ResponseGeneral {

    private ParametrosModel parametros;
}