package com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleInventario.infrastructure.persistence.model.DetalleInventarioModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaDetalleInventario extends ResponseGeneral implements Serializable {
    private List<DetalleInventarioModel> detalles;
}
