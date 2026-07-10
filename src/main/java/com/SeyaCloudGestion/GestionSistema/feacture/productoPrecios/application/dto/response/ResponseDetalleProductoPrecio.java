package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.model.ProductoPrecioModel;
import lombok.Data;

@Data
public class ResponseDetalleProductoPrecio extends ResponseGeneral {
    private ProductoPrecioModel productoPrecio;
}