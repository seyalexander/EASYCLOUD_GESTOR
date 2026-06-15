package com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.serieCaja.infraestructure.persistence.model.SerieCajaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaSerieCaja extends ResponseGeneral implements Serializable {

    private List<SerieCajaModel> serieCajas;
}