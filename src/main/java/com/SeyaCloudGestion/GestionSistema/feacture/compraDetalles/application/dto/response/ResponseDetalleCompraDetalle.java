package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.model.CompraDetalleModel;
import lombok.Data;

@Data
public class ResponseDetalleCompraDetalle extends ResponseGeneral {

    private CompraDetalleModel compraDetalle;
}