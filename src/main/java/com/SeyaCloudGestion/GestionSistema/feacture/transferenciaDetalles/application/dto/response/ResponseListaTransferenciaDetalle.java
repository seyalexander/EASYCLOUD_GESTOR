package com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.transferenciaDetalles.infraestructure.persistence.model.DetalleTransferenciaModel;
import lombok.Data;

import java.util.List;

@Data
public class ResponseListaTransferenciaDetalle extends ResponseGeneral {
    List<DetalleTransferenciaModel>detalles;
}
