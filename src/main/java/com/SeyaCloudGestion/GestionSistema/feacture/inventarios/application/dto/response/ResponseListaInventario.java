package com.SeyaCloudGestion.GestionSistema.feacture.inventarios.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.inventarios.infraestructure.persistence.model.InventarioModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaInventario extends ResponseGeneral implements Serializable {

    private List<InventarioModel> inventarios;
}