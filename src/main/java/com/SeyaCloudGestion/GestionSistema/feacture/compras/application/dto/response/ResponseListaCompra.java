package com.SeyaCloudGestion.GestionSistema.feacture.compras.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.compras.infraestructure.persistence.model.CompraModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaCompra extends ResponseGeneral implements Serializable {

    private List<CompraModel> compras;
}