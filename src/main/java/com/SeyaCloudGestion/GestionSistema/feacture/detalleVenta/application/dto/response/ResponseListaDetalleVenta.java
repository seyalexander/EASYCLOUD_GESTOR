package com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.detalleVenta.infrastructure.persistence.model.DetalleVentaModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaDetalleVenta extends ResponseGeneral implements Serializable {
    private List<DetalleVentaModel> detalles;
}
