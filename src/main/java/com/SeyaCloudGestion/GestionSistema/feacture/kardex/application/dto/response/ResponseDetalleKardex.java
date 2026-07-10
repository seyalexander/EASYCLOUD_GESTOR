package com.SeyaCloudGestion.GestionSistema.feacture.kardex.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.kardex.infraestructure.persistence.model.KardexModel;
import lombok.Data;

@Data
public class ResponseDetalleKardex extends ResponseGeneral {
    KardexModel kardex;
}
