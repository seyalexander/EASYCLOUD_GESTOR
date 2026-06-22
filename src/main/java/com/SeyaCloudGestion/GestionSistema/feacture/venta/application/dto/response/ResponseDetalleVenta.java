package com.SeyaCloudGestion.GestionSistema.feacture.venta.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.venta.infraestructure.persistence.model.VentaModel;
import lombok.Data;

@Data
public class ResponseDetalleVenta extends ResponseGeneral {

    private VentaModel detalles;
}