package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.model.subFamiliaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaSubFamilia extends ResponseGeneral implements Serializable {
    private List<subFamiliaModel> subfamilias;
}
