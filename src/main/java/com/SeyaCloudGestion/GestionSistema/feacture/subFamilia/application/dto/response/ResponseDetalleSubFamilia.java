package com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.subFamilia.infraestructure.persistence.model.subFamiliaModel;
import lombok.Data;

@Data
public class ResponseDetalleSubFamilia extends ResponseGeneral {
    private subFamiliaModel subFamilia;
}
