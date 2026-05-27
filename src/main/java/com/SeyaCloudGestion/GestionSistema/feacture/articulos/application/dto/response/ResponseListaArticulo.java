package com.SeyaCloudGestion.GestionSistema.feacture.articulos.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.articulos.infraestructure.persistence.model.ArticulosModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaArticulo extends ResponseGeneral implements Serializable {

    private List<ArticulosModel> articulos;
}