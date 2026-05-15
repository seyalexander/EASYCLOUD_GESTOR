package com.SeyaCloudGestion.GestionSistema.feacture.moneda.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.moneda.infraestructure.persistence.model.MonedaModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaMoneda extends ResponseGeneral {
    List<MonedaModel> monedas;
}
