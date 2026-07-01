package com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleCompra.infrastructure.persistence.model.DetalleCompraModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaDetalleCompra extends ResponseGeneral implements Serializable {
    private List<DetalleCompraModel> detalles;
}
