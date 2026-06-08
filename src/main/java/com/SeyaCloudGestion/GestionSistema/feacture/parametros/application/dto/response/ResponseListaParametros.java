package com.SeyaCloudGestion.GestionSistema.feacture.parametros.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.parametros.infraestructure.persistence.model.ParametrosModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaParametros extends ResponseGeneral implements Serializable {

    private List<ParametrosModel> parametros;
}