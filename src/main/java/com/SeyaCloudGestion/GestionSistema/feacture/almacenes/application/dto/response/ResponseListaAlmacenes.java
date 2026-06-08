package com.SeyaCloudGestion.GestionSistema.feacture.almacenes.application.dto.response;

import com.SeyaCloudGestion.GestionSistema.common.response.ResponseGeneral;
import com.SeyaCloudGestion.GestionSistema.feacture.almacenes.infraestructure.persistence.model.AlmacenesModel;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ResponseListaAlmacenes extends ResponseGeneral implements Serializable {

    private List<AlmacenesModel> almacenes;
}