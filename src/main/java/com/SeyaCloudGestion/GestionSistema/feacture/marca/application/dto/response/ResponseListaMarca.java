package com.SeyaCloudGestion.GestionSistema.feacture.marca.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.marca.infraestructure.persistence.model.MarcaModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaMarca extends ResponseGeneral {
    private List<MarcaModel> marcas;
}
