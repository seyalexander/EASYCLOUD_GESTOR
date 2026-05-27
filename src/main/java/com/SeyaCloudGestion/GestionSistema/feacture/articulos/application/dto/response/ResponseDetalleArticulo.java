package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.model.ArticulosModel;
import lombok.Data;

@Data
public class ResponseDetalleArticulo extends ResponseGeneral {

    private ArticulosModel articulos;
}