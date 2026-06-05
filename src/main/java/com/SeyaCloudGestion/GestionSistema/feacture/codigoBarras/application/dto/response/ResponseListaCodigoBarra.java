package com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.codigoBarras.infraestructure.persistence.model.CodigoBarraModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCodigoBarra extends ResponseGeneral implements Serializable {

    private List<CodigoBarraModel> codigoBarras;
}