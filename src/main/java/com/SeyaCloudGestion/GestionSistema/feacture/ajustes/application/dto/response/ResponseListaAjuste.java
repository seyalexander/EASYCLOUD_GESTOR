package com.SeyaCloudGestion.GestionSistema.feacture.ajustes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.ajustes.infraestructure.persistence.model.AjustesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaAjuste extends ResponseGeneral implements Serializable {

    private List<AjustesModel> ajustes;
}