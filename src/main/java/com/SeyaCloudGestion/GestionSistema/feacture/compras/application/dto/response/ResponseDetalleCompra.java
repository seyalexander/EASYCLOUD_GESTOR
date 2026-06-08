package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.model.CompraModel;
import lombok.Data;

@Data
public class ResponseDetalleCompra extends ResponseGeneral {

    private CompraModel compra;
}