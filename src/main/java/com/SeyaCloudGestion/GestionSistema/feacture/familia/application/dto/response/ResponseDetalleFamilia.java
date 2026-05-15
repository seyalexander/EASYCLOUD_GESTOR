package com.SeyaCloudGestion.GestionSistema.feacture.familia.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.familia.infraestructure.persistence.model.FamiliaModel;
import lombok.Data;

@Data
public class ResponseDetalleFamilia extends ResponseGeneral {
    FamiliaModel familia;
}
