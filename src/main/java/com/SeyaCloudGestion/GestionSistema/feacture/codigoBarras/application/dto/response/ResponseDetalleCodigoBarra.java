package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.model.CodigoBarraModel;
import lombok.Data;

@Data
public class ResponseDetalleCodigoBarra extends ResponseGeneral {

    private CodigoBarraModel codigoBarra;
}