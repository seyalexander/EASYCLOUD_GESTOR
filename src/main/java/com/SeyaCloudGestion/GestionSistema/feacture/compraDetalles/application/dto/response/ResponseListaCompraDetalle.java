package com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.compraDetalles.infraestructure.persistence.model.CompraDetalleModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCompraDetalle extends ResponseGeneral implements Serializable {

    private List<CompraDetalleModel> compraDetalles;
}