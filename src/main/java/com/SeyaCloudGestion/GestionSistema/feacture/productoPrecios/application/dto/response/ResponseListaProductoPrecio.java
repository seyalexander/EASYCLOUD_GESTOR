package com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.productoPrecios.infraestructure.persistence.model.ProductoPrecioModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaProductoPrecio extends ResponseGeneral implements Serializable {

    private List<ProductoPrecioModel> productoPrecios;
}