package com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.tipoMovimientos.infraestructure.persistence.model.TipoMovimientoModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaTipoMovimiento extends ResponseGeneral implements Serializable {

    private List<TipoMovimientoModel> tipoMovimientos;
}