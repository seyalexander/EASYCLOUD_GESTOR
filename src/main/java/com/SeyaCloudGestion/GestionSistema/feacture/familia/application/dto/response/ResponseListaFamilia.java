package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaFamilia extends ResponseGeneral implements Serializable {
    private List<FamiliaModel> familia;
}
