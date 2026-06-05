package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.model.ProductoImpuestoModel;
import lombok.Data;

@Data
public class ResponseDetalleProductoImpuesto extends ResponseGeneral {

    private ProductoImpuestoModel productoImpuesto;
}