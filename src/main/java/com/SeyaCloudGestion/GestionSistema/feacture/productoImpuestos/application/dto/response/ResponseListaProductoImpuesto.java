package com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.productoImpuestos.infraestructure.persistence.model.ProductoImpuestoModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaProductoImpuesto extends ResponseGeneral implements Serializable {

    private List<ProductoImpuestoModel> productoImpuestos;
}